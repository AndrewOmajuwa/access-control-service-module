package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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
class ProfileBusinessFunctionPermissionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;
    @Autowired
    Utility utility;
    @Autowired
    @MockBean
    private CheckPermissionService checkPermissionServiceMock;

    @BeforeEach
    public void setUp(){

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);

    }


    @Test
    @DisplayName("Save creates Profile Business Function Permission when successfull")
    void save_BusinessFunctionPermission_WhenSuccessfull() {

        Integer expectedId = 1;
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission = utility.createProfileBusinessFunctionPermission();
        Assertions.assertThat(profileBusinessFunctionPermission).isNotNull();
        Assertions.assertThat(profileBusinessFunctionPermission.getId()).isNotNull();
        Assertions.assertThat(profileBusinessFunctionPermission.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save does not create Profile Business Function Permission when Business Function Permission does not exist")
    void saveDoesNot_createProfileBusinessFunctionPermission_WhenBusinessFunctionPermissionDoesNotExist() {

        ResponseEntity<ProfileBusinessFunctionPermissionResponse> profileBusinessFunctionPermission = testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createBusinessFunctionPermissionNotToBeSaved1()), ProfileBusinessFunctionPermissionResponse.class);

        Assertions.assertThat(profileBusinessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Profile Business Function Permission when profile does not exist")
    void saveDoesNot_createProfileBusinessFunctionPermission_WhenProfileDoesNotExist() {

        ResponseEntity<ProfileBusinessFunctionPermissionResponse> profileBusinessFunctionPermission = testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createProfileBusinessFunctionPermissionNotToBeSaved2()), ProfileBusinessFunctionPermissionResponse.class);

        Assertions.assertThat(profileBusinessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Profile Business Function Permission when already present")
    void doesNotSave_BusinessFunctionPermission_WhenAlreadyPresent() {

        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission1 = utility.createProfileBusinessFunctionPermission();
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission2 = utility.createProfileBusinessFunctionPermission();
        Assertions.assertThat(profileBusinessFunctionPermission1.getId()).isEqualTo(profileBusinessFunctionPermission2.getId());
        Assertions.assertThat(profileBusinessFunctionPermissionRepository.findById(7)).isEmpty();
    }
    ProfileBusinessFunctionPermissionPostRequest createBusinessFunctionPermissionNotToBeSaved1() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(0).profileId(1)
                .build();
    }
    ProfileBusinessFunctionPermissionPostRequest createProfileBusinessFunctionPermissionNotToBeSaved2() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(1).profileId(0)
                .build();
    }
}