package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.requests.post.UserPostRequest;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ValidateAccessControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private KeycloakAdminClient keycloakAdminClient;

    @BeforeEach
    public void setUp(){
        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();

        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn("48553c16-56e4-42e6-8cf4-25cee7609a33");

    }

    @Test
    @DisplayName("Validate access endpoint returns http status 200 when user has valid credentials")
    void validateAccessEndpoint_returnsHttpStatus200_whenUserHasValidCredentials() {

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/validate-access?applicationName=access-control-service&functionName=permission&permission=create", HttpMethod.GET, null, Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    @DisplayName("Validate access endpoint returns http status 403 when user has invalid credentials")
    void validateAccessEndpoint_returnsHttpStatus403_whenUserHasInvalidCredentials() {

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/validate-access?applicationName=access-control-service&functionName=profile&permission=delete", HttpMethod.GET, null, Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Validate access endpoint returns http status 404 when permission or business function does not exist")
    void validateAccessEndpoint_returnsHttpStatus404_whenUserHasInvalidCredentials() {

        ResponseEntity<Void> status = testRestTemplate.exchange("/api/v1/validate-access?applicationName=doctor-service&functionName=userProfile&permission=update", HttpMethod.GET, null, Void.class);

        Assertions.assertThat(status.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}