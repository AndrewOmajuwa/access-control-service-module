package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.SetUpPostRequest;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SetUpControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private Utility utility;

    @Value("${user.loggedInUserKeycloakUuid}")
    private String loggedInUserKeycloakUuid;

    @MockBean
    private KeycloakAdminClient keycloakAdminClient;

    @BeforeEach
    public void setUp(){
        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();
        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn(loggedInUserKeycloakUuid);
    }

    @Test
    @DisplayName("Set up returns http status Ok when successfully executed")
    void setUp_returnsHttpstatus200_whenSuccessfullyExecuted(){

        utility.createUser();

        SetUpPostRequest setUpPostRequest = SetUpPostRequest.builder().applicationName("access-control-service").functionName("business-function").profileName("view").permission("view").email("admin@user").build();

        ResponseEntity<HttpStatus> responseEntity = testRestTemplate.exchange("/api/v1/setup-users", HttpMethod.POST, Utility.createJsonHttpEntity(setUpPostRequest), HttpStatus.class);

        Assertions.assertThat(responseEntity.getBody()).isEqualTo(HttpStatus.OK);

    }
    @Test
    @DisplayName("Set up returns http status Bad Request when email doesnt exist")
    void setUp_returnsBadRequest_whenEmailDoesntExist(){

        utility.createUser();

        SetUpPostRequest setUpPostRequest = SetUpPostRequest.builder().applicationName("access-control-service").functionName("business-function").profileName("view").permission("view").email("eric.cartman@emaillll.com").build();

        ResponseEntity<HttpStatus> responseEntity = testRestTemplate.exchange("/api/v1/setup-users", HttpMethod.POST, Utility.createJsonHttpEntity(setUpPostRequest), HttpStatus.class);

        Assertions.assertThat(responseEntity.getBody()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}