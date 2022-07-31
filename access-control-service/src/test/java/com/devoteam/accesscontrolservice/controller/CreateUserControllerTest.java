package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
import com.devoteam.accesscontrolservice.domain.UserResponse;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUserControllerTest {

    @Value("${user.loggedInUserKeycloakUuid}")
    private String loggedInUserKeycloakUuid;
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
        @DisplayName("Save creates user when successfull")
        public void save_User_WhenSuccessfull(){

            UserResponse userResponse = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createUserKeycloakToBeSaved()), UserResponse.class).getBody();
            Assertions.assertThat(userResponse).isNotNull();
            Assertions.assertThat(userResponse.getUuid()).isNotNull();
        }


}