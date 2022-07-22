package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidateAccessControllerTest {
    @Value("${user.loggedInUserKeycloakUuid}")
    private UUID loggedInUserKeycloakUuid;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private KeycloakAdminClient keycloakAdminClient;

    @BeforeEach
    public void setUp(){
        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();
        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn(loggedInUserKeycloakUuid);
    }

    @Test
    @DisplayName("Validate access endpoint returns http status 200 when user has valid credentials")
    void validateAccessEndpoint_returnsHttpStatus200_whenUserHasValidCredentials() {

        createProfile();
        createUserProfile(loggedInUserKeycloakUuid);
        createBusinessFunction();
        createPermission();
        createBusinessFunctionPermission();
        createProfileBusinessFunctionPermission();
        ValidateAccessPostRequest validateAccessPostRequest = ValidateAccessPostRequest.builder().applicationName("Doctor-Service").functionName("Doctor").permission("View").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/validate-access", HttpMethod.POST, Utility.createJsonHttpEntity(validateAccessPostRequest), Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    @DisplayName("Validate access endpoint returns http status 403 when user has invalid credentials")
    void validateAccessEndpoint_returnsHttpStatus200_whenUserHasInvalidCredentials() {

        UserResponse user = createUser();
        createBusinessFunction();
        createPermission();
        testRestTemplate.exchange("/api/v1/permissions", HttpMethod.POST, Utility.createJsonHttpEntity(Permission.builder().name("delete").build()), BusinessFunctionResponse.class);
        createBusinessFunctionPermission();
        createProfile();
        createProfileBusinessFunctionPermission();
        createUserProfile(user.getUuid());
        ValidateAccessPostRequest validateAccessPostRequest = ValidateAccessPostRequest.builder().applicationName("Doctor-Service").functionName("Doctor").permission("delete").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/validate-access", HttpMethod.POST, Utility.createJsonHttpEntity(validateAccessPostRequest), Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Validate access endpoint returns http status 404 when permission or business function does not exist")
    void validateAccessEndpoint_returnsHttpStatus404_whenUserHasInvalidCredentials() {

        ValidateAccessPostRequest validateAccessPostRequest = ValidateAccessPostRequest.builder().applicationName("Doctor-").functionName("Doctor").permission("delete").build();

        ResponseEntity<Void> status = testRestTemplate.exchange("/api/v1/validate-access", HttpMethod.POST, Utility.createJsonHttpEntity(validateAccessPostRequest), Void.class);

        Assertions.assertThat(status.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    UserResponse createUser() {
        UserPostRequest user = Utility.createUserKeycloakToBeSaved();
        return testRestTemplate
                .exchange("/api/v1/users", HttpMethod.POST, Utility.createUserJsonHttpEntity(user), UserResponse.class).getBody();
    }
    public static UserProfilePostRequest createUserProfileToBeSaved(UUID uuid) {
        return UserProfilePostRequest.builder().userKeyCloakId(uuid).profileId(1)
                .build();
    }

    void createBusinessFunction() {
        BusinessFunctionPostRequest businessFunction = Utility.createBusinessFunctionToBeSaved();
        testRestTemplate
                .exchange("/api/v1/business-functions", HttpMethod.POST, Utility.createJsonHttpEntity(businessFunction), BusinessFunctionResponse.class);
    }

    void createPermission() {
        PermissionPostRequest permission = Utility.createPermissionToBeSaved();

        testRestTemplate
                .exchange("/api/v1/permissions", HttpMethod.POST, Utility.createJsonHttpEntity(permission), PermissionResponse.class);
    }
    void createBusinessFunctionPermission() {
        BusinessFunctionPermissionPostRequest businessFunctionPermission = Utility.createBusinessFunctionPermissionToBeSaved();
        testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(businessFunctionPermission), BusinessFunctionPermissionResponse.class);
    }

    void createProfile() {
        ProfilePostRequest profile = Utility.createProfileToBeSaved();
        testRestTemplate
                .exchange("/api/v1/profiles", HttpMethod.POST, Utility.createJsonHttpEntity(profile), ProfileResponse.class).getBody();
    }


    void createProfileBusinessFunctionPermission() {
        testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createProfileBusinessFunctionPermissionToBeSaved()), ProfileBusinessFunctionPermissionResponse.class);
    }

    void createUserProfile(UUID uuid) {
         testRestTemplate
                .exchange("/api/v1/user-profiles", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createUserProfileToBeSaved(uuid)), UserProfileResponse.class);
    }
}