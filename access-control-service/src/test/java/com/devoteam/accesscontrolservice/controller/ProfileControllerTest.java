package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.ProfilePostRequest;
import com.devoteam.accesscontrolservice.domain.ProfileResponse;
import com.devoteam.accesscontrolservice.repository.ProfileRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private Utility utility;

    @Test
    @DisplayName("Save creates Profile when successfull")
    public void save_Profile_WhenSuccessfull(){

        Integer expectedId = 1;
        ProfileResponse profileResponse = utility.createProfile();
        Assertions.assertThat(profileResponse).isNotNull();
        Assertions.assertThat(profileResponse.getId()).isNotNull();
        Assertions.assertThat(profileResponse.getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Save does not create Profile when input is blank")
    public void save_DoesNotCreateProfile_WhenInputIsBlank(){

        ProfileResponse profileResponse = testRestTemplate
                .exchange( "/api/v1/profiles", HttpMethod.POST, Utility.createJsonHttpEntity(createProfileNotToBeSaved()), ProfileResponse.class)
                .getBody();

        Assertions.assertThat(profileResponse.getId()).isNull();
    }

    @Test
    @DisplayName("Save does not create Profile when already present")
    public void doesNotSave_Profile_WhenAlreadyPresent(){

        ProfileResponse profileResponse1 = utility.createProfile();
        ProfileResponse profileResponse2 = utility.createProfile();
        Assertions.assertThat(profileResponse1.getId()).isEqualTo(profileResponse2.getId());
        Assertions.assertThat(profileRepository.findById(2)).isEmpty();
    }
    public ProfilePostRequest createProfileNotToBeSaved(){
        return ProfilePostRequest.builder()
                .name("")
                .build();
    }

}