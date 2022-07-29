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

    @Test
    @DisplayName("Save creates Profile when successfull")
    public void save_Profile_WhenSuccessfull(){

        ProfileResponse profileResponse = utility.createProfile();
        Assertions.assertThat(profileResponse).isNotNull();
        Assertions.assertThat(profileResponse.getId()).isNotNull();
        Assertions.assertThat(profileResponse.getId()).isEqualTo(2);
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
        Assertions.assertThat(profileRepository.findById(3)).isEmpty();
    }
    public ProfilePostRequest createProfileNotToBeSaved(){
        return ProfilePostRequest.builder()
                .name("")
                .build();
    }

}