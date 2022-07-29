package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.UserProfileRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserProfileControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private Utility utility;

    @MockBean
    private KeycloakAdminClient keycloakAdminClient;
    private static final java.util.UUID UUID = java.util.UUID.randomUUID();

    @BeforeEach
    public void setUp(){
        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();
        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn(UUID.toString());
    }

    @Test
    @DisplayName("Save creates User Profile when successfull")
    void save_UserProfile_WhenSuccessfull() {
        UserResponse user = utility.createUser();
        utility.createProfile();
        UserProfileResponse userProfileResponse = utility.createUserProfile(user.getUuid());
        Assertions.assertThat(userProfileResponse).isNotNull();
        Assertions.assertThat(userProfileResponse.getId()).isNotNull();
        Assertions.assertThat(userProfileResponse.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save does not create User Profile when User Keycloak does not exist")
    void saveDoesNot_SaveUserProfile_WhenUserKeycloakDoesNotExist() {
        ResponseEntity<UserProfileResponse> userProfile = testRestTemplate
                .exchange("/api/v1/user-profiles", HttpMethod.POST, Utility.createJsonHttpEntity(createUserProfileNotToBeSaved1()), UserProfileResponse.class);
        Assertions.assertThat(userProfile.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create User Profile when Profile does not exist")
    void saveDoesNot_SaveUserProfile_WhenProfileDoesNotExist() {
        ResponseEntity<UserProfileResponse> userProfile = testRestTemplate
                .exchange("/api/v1/user-profiles", HttpMethod.POST, Utility.createJsonHttpEntity(createUserProfileNotToBeSaved2()), UserProfileResponse.class);

        Assertions.assertThat(userProfile.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create User Profile when already present")
    void doesNotSave_UserProfile_WhenAlreadyPresent() {
        UserResponse user = utility.createUser();
        utility.createProfile();
        UserProfileResponse userProfileResponse1 = utility.createUserProfile(user.getUuid());
        UserProfileResponse userProfileResponse2 = utility.createUserProfile(user.getUuid());
        Assertions.assertThat(userProfileResponse1.getId()).isEqualTo(userProfileResponse2.getId());
        Assertions.assertThat(userProfileRepository.findById(2)).isEmpty();
    }
    
    public UserProfilePostRequest createUserProfileNotToBeSaved1() {
        return UserProfilePostRequest.builder().userKeyCloakId("0b00000f-ea0a-0b00-0000-00dff0000cb0").profileId(1)
                .build();
    }
    public UserProfilePostRequest createUserProfileNotToBeSaved2() {
        UserResponse userResponse = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, createJsonHttpEntity(Utility.createUserKeycloakToBeSaved()), UserResponse.class).getBody();
        return UserProfilePostRequest.builder().userKeyCloakId(userResponse.getUuid()).profileId(0)
                .build();
    }
    private HttpEntity<UserPostRequest> createJsonHttpEntity(UserPostRequest userPostRequest){
        return new HttpEntity<>(userPostRequest, utility.createJsonHeader());
    }
}