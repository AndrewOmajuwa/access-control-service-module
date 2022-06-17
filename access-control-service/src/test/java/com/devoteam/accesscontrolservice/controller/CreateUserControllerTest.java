package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.UUID;

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
        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail())).thenReturn(UUID);
    }

    @Test
    @DisplayName("Save creates user when successfull")
    public void save_User_WhenSuccessfull(){
        UserPostRequest userToBeSaved = creatUserToBeSaved();
        System.out.println(testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, createJsonHttpEntity(userToBeSaved), UserResponse.class).getBody().getUuid());

        UserResponse userResponse = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, createJsonHttpEntity(userToBeSaved), UserResponse.class).getBody();
        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getUuid()).isNotNull();
    }

    public UserPostRequest creatUserToBeSaved(){
        return UserPostRequest.builder()
                .firstName("Eric")
                .lastName("Cartman")
                .email("eric.cartman@email.com")
                .build();
    }

    private HttpEntity<UserPostRequest> createJsonHttpEntity(UserPostRequest userPostRequest){
        return new HttpEntity<>(userPostRequest, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}