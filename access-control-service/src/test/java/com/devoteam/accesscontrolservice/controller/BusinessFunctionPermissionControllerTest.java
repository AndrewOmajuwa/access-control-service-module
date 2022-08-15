package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import com.devoteam.accesscontrolservice.wrapper.PageableResponse;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BusinessFunctionPermissionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BusinessFunctionPermissionRepository businessFunctionPermissionRepository;
    @Autowired
    private Utility utility;
    @MockBean
    private CheckPermissionService checkPermissionServiceMock;

    @BeforeEach
    public void setUp(){

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);

    }
    
    @Test
    @DisplayName("Save creates Business Function Permission when successfull")
    void save_BusinessFunctionPermission_WhenSuccessfull() {

        Integer expectedId = 1;
        BusinessFunctionPermissionResponse businessFunctionPermission = utility.createBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission).isNotNull();
        Assertions.assertThat(businessFunctionPermission.getId()).isNotNull();
        Assertions.assertThat(businessFunctionPermission.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when Business Function does not exist")
    void saveDoesNot_SaveBusinessFunctionPermission_WhenBusinessFunctionDoesNotExist() {

        ResponseEntity<BusinessFunctionPermissionResponse> businessFunctionPermission = testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createBusinessFunctionPermissionNotToBeSaved1()), BusinessFunctionPermissionResponse.class);

        Assertions.assertThat(businessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when Permission does not exist")
    void saveDoesNot_SaveBusinessFunctionPermission_WhenPermissionDoesNotExist() {

        ResponseEntity<BusinessFunctionPermissionResponse> businessFunctionPermission = testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createBusinessFunctionPermissionNotToBeSaved2()), BusinessFunctionPermissionResponse.class);

        Assertions.assertThat(businessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when already present")
    void doesNotSave_BusinessFunctionPermission_WhenAlreadyPresent() {

        BusinessFunctionPermissionResponse businessFunctionPermission1 = utility.createBusinessFunctionPermission();
        BusinessFunctionPermissionResponse businessFunctionPermission2 = utility.createBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission1.getId()).isEqualTo(businessFunctionPermission2.getId());
        Assertions.assertThat(businessFunctionPermissionRepository.findById(7)).isEmpty();
    }


    @Test
    @DisplayName("findAll returns a paginated list of business-function-permissions when called successfully")
    void findAll_ReturnsListOfPaginatedBusinessFunctionPermissions_WhenCalledSuccessfully(){

        PageableResponse<BusinessFunctionPermission> businessFunctionPermissions = testRestTemplate.exchange("/api/v1/business-functions-permissions?applicationName={applicationName}", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<BusinessFunctionPermission>>() {
        }, "access-control-service").getBody();

        Assertions.assertThat(businessFunctionPermissions).isNotNull();

        Assertions.assertThat(businessFunctionPermissions).isNotEmpty();

        Assertions.assertThat(businessFunctionPermissions.stream().count()).isEqualTo(6);

    }


    public BusinessFunctionPermissionPostRequest createBusinessFunctionPermissionNotToBeSaved1() {
        return BusinessFunctionPermissionPostRequest.builder().businessFunctionId(0).permissionId(1)
                .build();
    }
    public BusinessFunctionPermissionPostRequest createBusinessFunctionPermissionNotToBeSaved2() {
        return BusinessFunctionPermissionPostRequest.builder().businessFunctionId(1).permissionId(0)
                .build();
    }
}