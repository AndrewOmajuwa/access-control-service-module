package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import com.devoteam.accesscontrolservice.service.ProfileService;
import com.devoteam.accesscontrolservice.service.SetUpService;
import com.devoteam.accesscontrolservice.util.Utility;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.mockito.Mockito.doAnswer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@NoArgsConstructor
class SetUpControllerTest {

    @SpyBean
    private ProfileService profileService;

    @Autowired
    @MockBean
    private CheckPermissionService checkPermissionServiceMock;

    @Autowired
    private BusinessFunctionRepository businessFunctionRepository;

    @Autowired
    BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

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

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);
    }

    @Test
    @DisplayName("Set up returns http status Ok when successfully executed")
    void setUp_returnsHttpstatus200_whenSuccessfullyExecuted(){

        utility.createUser();

        SetUpPostRequest setUpPostRequest = SetUpPostRequest.builder().applicationName("access-control-service").functionName("business-function").profileName("view").permission("view").email("admin@user").build();

        ResponseEntity<HttpStatus> responseEntity = testRestTemplate.exchange("/api/v1/setup-users", HttpMethod.POST, Utility.createJsonHttpEntity(setUpPostRequest), HttpStatus.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    @DisplayName("Set up returns http status Bad Request when email doesnt exist")
    void setUp_returnsBadRequest_whenEmailDoesntExist(){

        utility.createUser();

        SetUpPostRequest setUpPostRequest = SetUpPostRequest.builder().applicationName("access-control-service").functionName("business-function").profileName("view").permission("view").email("eric.cartman@emaillll.com").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/setup-users", HttpMethod.POST, Utility.createJsonHttpEntity(setUpPostRequest), Void.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    @DisplayName("Set up rollsback transaction when exception is thrown")
    void
    setUp_rollsBackTransaction_whenRuntimeExceptionIsThrown() throws RuntimeException{

        utility.createUser();

        SetUpPostRequest setUpPostRequest = SetUpPostRequest.builder().applicationName("access-control-service").functionName("business-function").profileName("view").permission("create").email("admin@user").build();

        BDDMockito.doThrow(new RuntimeException("Exception message")).when(profileService).save(ArgumentMatchers.any());

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/setup-users", HttpMethod.POST, Utility.createJsonHttpEntity(setUpPostRequest), Void.class);

        Assertions.assertThat(businessFunctionRepository.findAll()).hasSize(6);

        Assertions.assertThat(permissionRepository.findAll()).hasSize(1);

        Assertions.assertThat(businessFunctionPermissionRepository.findAll()).hasSize(6);

        System.out.println(responseEntity.getStatusCode());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotSameAs(HttpStatus.OK);

    }

    @Test
    @DisplayName("Set up returns 403 Forbidden when preAuthorize is executed")
    void setUp_returns403Forbidden_whenPreAuthorizeIsExecuted(){

        utility.createUser();

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(false);

        SetUpPostRequest setUpPostRequest = SetUpPostRequest.builder().applicationName("access-control-service").functionName("business-function").profileName("view").permission("view").email("admin@user").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/setup-users", HttpMethod.POST, Utility.createJsonHttpEntity(setUpPostRequest), Void.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}