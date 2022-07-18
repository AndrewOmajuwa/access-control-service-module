package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileBusinessFunctionPermissionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;

    private Utility utility;

    @Test
    @DisplayName("Save creates Profile Business Function Permission when successfull")
    void save_BusinessFunctionPermission_WhenSuccessfull() {

        createBusinessFunction();
        createPermission();
        createBusinessFunctionPermission();
        createProfile();
        Integer expectedId = 1;
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission = createProfileBusinessFunctionPermission();
        Assertions.assertThat(profileBusinessFunctionPermission).isNotNull();
        Assertions.assertThat(profileBusinessFunctionPermission.getId()).isNotNull();
        Assertions.assertThat(profileBusinessFunctionPermission.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save does not create Profile Business Function Permission when Business Function Permission does not exist")
    void saveDoesNot_createProfileBusinessFunctionPermission_WhenBusinessFunctionPermissionDoesNotExist() {

        ResponseEntity<ProfileBusinessFunctionPermissionResponse> profileBusinessFunctionPermission = testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createBusinessFunctionPermissionNotToBeSaved1()), ProfileBusinessFunctionPermissionResponse.class);

        Assertions.assertThat(profileBusinessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Profile Business Function Permission when profile does not exist")
    void saveDoesNot_createProfileBusinessFunctionPermission_WhenProfileDoesNotExist() {

        ResponseEntity<ProfileBusinessFunctionPermissionResponse> profileBusinessFunctionPermission = testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createProfileBusinessFunctionPermissionNotToBeSaved2()), ProfileBusinessFunctionPermissionResponse.class);

        Assertions.assertThat(profileBusinessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Profile Business Function Permission when already present")
    void doesNotSave_BusinessFunctionPermission_WhenAlreadyPresent() {

        createBusinessFunctionPermission();
        createProfile();
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission1 = createProfileBusinessFunctionPermission();
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission2 = createProfileBusinessFunctionPermission();
        Assertions.assertThat(profileBusinessFunctionPermission1.getId()).isEqualTo(profileBusinessFunctionPermission2.getId());
        Assertions.assertThat(profileBusinessFunctionPermissionRepository.findById(2)).isEmpty();
    }

    private void createBusinessFunction() {
        BusinessFunctionPostRequest businessFunction = Utility.createBusinessFunctionToBeSaved();
        testRestTemplate
                .exchange("/api/v1/business-functions", HttpMethod.POST, Utility.createJsonHttpEntity(businessFunction), BusinessFunctionResponse.class);
    }

    private void createPermission() {
        PermissionPostRequest permission = Utility.createPermissionToBeSaved();

        testRestTemplate
                .exchange("/api/v1/permissions", HttpMethod.POST, Utility.createJsonHttpEntity(permission), BusinessFunctionResponse.class);
    }
    private void createProfile() {
        ProfilePostRequest profile = Utility.createProfileToBeSaved();
        testRestTemplate
                .exchange("/api/v1/profiles", HttpMethod.POST, Utility.createJsonHttpEntity(profile), ProfileResponse.class);
    }

    private void createBusinessFunctionPermission() {
        BusinessFunctionPermissionPostRequest businessFunctionPermission = Utility.createBusinessFunctionPermissionToBeSaved();
        testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(businessFunctionPermission), BusinessFunctionPermissionResponse.class);
    }


    public ProfileBusinessFunctionPermissionResponse createProfileBusinessFunctionPermission() {
        return testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createProfileBusinessFunctionPermissionToBeSaved()), ProfileBusinessFunctionPermissionResponse.class)
                .getBody();
    }

    public ProfileBusinessFunctionPermissionPostRequest createProfileBusinessFunctionPermissionToBeSaved() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(1).profileId(1)
                .build();
    }
    public ProfileBusinessFunctionPermissionPostRequest createBusinessFunctionPermissionNotToBeSaved1() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(0).profileId(1)
                .build();
    }
    public ProfileBusinessFunctionPermissionPostRequest createProfileBusinessFunctionPermissionNotToBeSaved2() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(1).profileId(0)
                .build();
    }
}