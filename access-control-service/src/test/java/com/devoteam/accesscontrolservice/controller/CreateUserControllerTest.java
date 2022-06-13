package com.devoteam.accesscontrolservice.controller;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp(){
        BDDMockito.when(userRepositoryMock.save(creatUserToBeSaved())).thenReturn(creatUser());
    }

    @Test
    @DisplayName("Save creates user when successfull")
    public void save_User_WhenSuccessfull(){
        User userToBeSaved = creatUserToBeSaved();
        User user = testRestTemplate.exchange("http://localhost:8090/users", HttpMethod.POST, createJsonHttpEntity(userToBeSaved), User.class).getBody();
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUuid()).isNotNull();
    }

    public User creatUser(){
        return User.builder()
                .uuid(UUID.fromString("f1684b3e-a09d-4085-87f0-c709ec99c183"))
                .firstName("Eric")
                .lastName("Cartman")
                .email("eric.cartman@email.com")
                .build();
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