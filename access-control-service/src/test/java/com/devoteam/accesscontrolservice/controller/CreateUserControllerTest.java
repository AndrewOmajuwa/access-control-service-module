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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUserControllerTest {

        private static final java.util.UUID UUID = java.util.UUID.randomUUID();
        @Autowired
        private TestRestTemplate testRestTemplate;
        @MockBean
        private KeycloakAdminClient keycloakAdminClient;

        @BeforeEach
        public void setUp(){
            UserPostRequest userPostRequest = creatUserToBeSaved();
            BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn(UUID);
        }

        @Test
        @DisplayName("Save creates user when successfull")
        public void save_User_WhenSuccessfull(){
            UserPostRequest userToBeSaved = creatUserToBeSaved();

            UserResponse userResponse = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, createJsonHttpEntity(userToBeSaved), UserResponse.class).getBody();
            Assertions.assertThat(userResponse).isNotNull();
            Assertions.assertThat(userResponse.getUuid()).isNotNull();
        }

        public UserPostRequest creatUserToBeSaved(){
            return UserPostRequest.builder()
                    .firstName("Eric")
                    .lastName("Cartman")
                    .email("eric.cartman@email.com")
                    .password("password")
                    .build();
        }

        private HttpEntity<UserPostRequest> createJsonHttpEntity(UserPostRequest userPostRequest){
            return new HttpEntity<>(userPostRequest, Utility.createJsonHeader());
        }

}