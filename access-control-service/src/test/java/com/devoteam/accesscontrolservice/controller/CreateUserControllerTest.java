package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.User;
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
        User user = creatUserToBeSaved();
        BDDMockito.when(keycloakAdminClient.createUserUuid(user.getFirstName(), user.getLastName(), user.getEmail())).thenReturn(UUID);
    }

    @Test
    @DisplayName("Save creates user when successfull")
    public void save_User_WhenSuccessfull(){
        User userToBeSaved = creatUserToBeSaved();
        User user = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, createJsonHttpEntity(userToBeSaved), User.class).getBody();
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUuid()).isNotNull();
    }

    public User creatUserToBeSaved(){
        return User.builder()
                .firstName("Eric")
                .lastName("Cartman")
                .email("eric.cartman@email.com")
                .build();
    }

    private HttpEntity<User> createJsonHttpEntity(User user){
        return new HttpEntity<>(user, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}