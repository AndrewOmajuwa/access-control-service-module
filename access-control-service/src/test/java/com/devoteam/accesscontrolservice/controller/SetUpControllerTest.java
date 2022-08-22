package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.requests.post.SetUpPostRequest;
import com.devoteam.accesscontrolservice.requests.post.UserPostRequest;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import com.devoteam.accesscontrolservice.service.ProfileService;
import com.devoteam.accesscontrolservice.util.UtilityTest;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@NoArgsConstructor
class SetUpControllerTest {

    @SpyBean
    private ProfileService profileService;

    @MockBean
    private CheckPermissionService checkPermissionServiceMock;

    @Autowired
    private BusinessFunctionRepository businessFunctionRepository;

    @Autowired
    BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UtilityTest utilityTest;

    @MockBean
    private KeycloakAdminClient keycloakAdminClient;

    @BeforeEach
    public void setUp(){
        UserPostRequest userPostRequest = UtilityTest.createUserKeycloakToBeSaved();

        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn("48553c16-56e4-42e6-8cf4-25cee7609a33");

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);
    }

    @Test
    @DisplayName("Set up returns http status Ok when successfully executed")
    void setUp_returnsHttpstatus200_whenSuccessfullyExecuted(){

        utilityTest.createUser();

        SetUpPostRequest setUpPostRequest = UtilityTest.createSetUpToBeSaved();

        ResponseEntity<Void> responseEntity = utilityTest.createSetUpTestRestTemplate(setUpPostRequest);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Set up returns http status Bad Request when email doesnt exist")
    void setUp_returnsBadRequest_whenEmailDoesntExist(){

        utilityTest.createUser();

        SetUpPostRequest setUpPostRequest = UtilityTest.createSetUpNotToBeSaved();

        ResponseEntity<Void> responseEntity = utilityTest.createSetUpTestRestTemplate(setUpPostRequest);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Set up rollsback transaction when exception is thrown")
    void
    setUp_rollsBackTransaction_whenRuntimeExceptionIsThrown() throws RuntimeException{

        utilityTest.createUser();

        SetUpPostRequest setUpPostRequest = UtilityTest.createSetUpToBeSaved();

        BDDMockito.doThrow(new RuntimeException("Exception message")).when(profileService).save(ArgumentMatchers.any());

        ResponseEntity<Void> responseEntity = utilityTest.createSetUpTestRestTemplate(setUpPostRequest);

        Assertions.assertThat(businessFunctionRepository.findAll()).hasSize(7);

        Assertions.assertThat(permissionRepository.findAll()).hasSize(2);

        Assertions.assertThat(businessFunctionPermissionRepository.findAll()).hasSize(6);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotSameAs(HttpStatus.OK);

    }

    @Test
    @DisplayName("Set up returns 403 Forbidden when preAuthorize is executed")
    void setUp_returns403Forbidden_whenPreAuthorizeIsExecuted(){

        utilityTest.createUser();

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(false);

        SetUpPostRequest setUpPostRequest = UtilityTest.createSetUpToBeSaved();

        ResponseEntity<Void> responseEntity = utilityTest.createSetUpTestRestTemplate(setUpPostRequest);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}