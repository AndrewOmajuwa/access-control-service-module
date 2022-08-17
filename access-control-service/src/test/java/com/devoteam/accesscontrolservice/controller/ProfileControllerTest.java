package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.ProfileRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import com.devoteam.accesscontrolservice.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProfileControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private Utility utility;
    @MockBean
    private CheckPermissionService checkPermissionServiceMock;
    @MockBean
    private KeycloakAdminClient keycloakAdminClient;

    @BeforeEach
    public void setUp(){
        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();

        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn("48553c16-56e4-42e6-8cf4-25cee7609a33");

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);

    }

    @Test
    @DisplayName("Save creates Profile when successfull")
    void save_Profile_WhenSuccessfull(){

        ProfileResponse profileResponse = utility.createProfile();
        Assertions.assertThat(profileResponse).isNotNull();
        Assertions.assertThat(profileResponse.getId()).isNotNull();
        Assertions.assertThat(profileResponse.getId()).isEqualTo(2);
    }
    @Test
    @DisplayName("Save does not create Profile when input is blank")
    void save_DoesNotCreateProfile_WhenInputIsBlank(){

        ProfileResponse profileResponse = testRestTemplate
                .exchange( "/api/v1/profiles", HttpMethod.POST, Utility.createJsonHttpEntity(createProfileNotToBeSaved()), ProfileResponse.class)
                .getBody();

        Assertions.assertThat(profileResponse.getId()).isNull();
    }

    @Test
    @DisplayName("Save does not create Profile when already present")
    void doesNotSave_Profile_WhenAlreadyPresent(){

        ProfileResponse profileResponse1 = utility.createProfile();
        ProfileResponse profileResponse2 = utility.createProfile();
        Assertions.assertThat(profileResponse1.getId()).isEqualTo(profileResponse2.getId());
        Assertions.assertThat(profileRepository.findById(3)).isEmpty();
    }

    @Test
    @DisplayName("findAll returns a paginated list of profiles when called successfully")
    void findAll_ReturnsListOfPaginatedProfiles_WhenCalledSuccessfully(){

        PageableResponse<Profile> profiles = testRestTemplate.exchange("/api/v1/profiles", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Profile>>() {
        }).getBody();

        Assertions.assertThat(profiles).isNotNull();

        Assertions.assertThat(profiles).isNotEmpty();

        Assertions.assertThat(profiles.toList().get(0).getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("findById returns a Profile when successfull")
    void findById_ReturnsProfile_WhenSuccessfull(){

        ResponseEntity<Profile> profile = testRestTemplate.exchange("/api/v1/profiles/1", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(profile).isNotNull();

        Assertions.assertThat(profile.getBody().getId()).isNotNull();

        Assertions.assertThat(profile.getBody().getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("findById returns 404 Not Found when id doesnt exist")
    void findById_Returns404NotFound_WhenIdDoesntExist(){

        ResponseEntity<Profile> profile = testRestTemplate.exchange("/api/v1/profiles/2", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(profile.getBody().getId()).isNull();

        Assertions.assertThat(profile.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }


    @Test
    @DisplayName("updated profile replaces existing profile when successfully executed")
    void updatedProfileo_ReplacesExistingProfile_WhenSuccessfullyExecuted(){

        Profile updatedProfile = Profile.builder().id(1).name("Updated-Name").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/profiles", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedProfile), Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(profileRepository.findById(1).get().getName()).isEqualTo("Updated-Name");

    }

    @Test
    @DisplayName("update profile returns 400 BadRequest when profile name is null or blank")
    void updatedProfile_Returns400BadRequest_WhenProfileNameIsNullOrBlank(){

        Profile updatedProfile = Profile.builder().id(1).name(null).build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/profiles", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedProfile), Void.class);

        Assertions.assertThat(responseEntity.getBody()).isNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    @DisplayName("update profile returns 404 ResourceNotfound when profile id does not exist")
    void updatedProfile_Returns400BadRequest_WhenProfileIdDoesNotExist(){

        Profile updatedProfile = Profile.builder().id(100).name("Updated-Name").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/profiles", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedProfile), Void.class);

        Assertions.assertThat(responseEntity.getBody()).isNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
    @Test
    @DisplayName("profile does not get updated when profile name is not unique")
    void Profile_DoesNotGetUpdated_WhenProfileNameIsNotUnique(){

        Profile updatedProfile1 = Profile.builder().id(1).name("Updated-Name").build();

        Profile updatedProfile2 = Profile.builder().id(2).name("Updated-Name").build();

        testRestTemplate.exchange("/api/v1/profiles", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedProfile1), Void.class);

        ResponseEntity<Void> responseEntity2 = testRestTemplate.exchange("/api/v1/profiles", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedProfile2), Void.class);

        Assertions.assertThat(responseEntity2.getStatusCode()).isNotEqualTo(HttpStatus.NO_CONTENT);

    }


    public ProfilePostRequest createProfileNotToBeSaved(){
        return ProfilePostRequest.builder()
                .name("")
                .build();
    }

}