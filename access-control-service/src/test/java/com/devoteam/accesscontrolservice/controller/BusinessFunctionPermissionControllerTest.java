package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BusinessFunctionPermissionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BusinessFunctionPermissionRepository businessFunctionPermissionRepository;
    
    @Test
    @DisplayName("Save creates Business Function Permission when successfull")
    void save_BusinessFunctionPermission_WhenSuccessfull() {

        createBusinessFunction();
        createPermission();
        Integer expectedId = 1;
        BusinessFunctionPermissionResponse businessFunctionPermission = createTemplatePostBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission).isNotNull();
        Assertions.assertThat(businessFunctionPermission.getId()).isNotNull();
        Assertions.assertThat(businessFunctionPermission.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when Business Function does not exist")
    void saveDoesNot_SaveBusinessFunctionPermission_WhenBusinessFunctionDoesNotExist() {

        ResponseEntity<BusinessFunctionPermissionResponse> businessFunctionPermission = testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, createJsonHttpEntityBusinessFucntionPermission(createBusinessFunctionPermissionNotToBeSaved1()), BusinessFunctionPermissionResponse.class);

        Assertions.assertThat(businessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when Permission does not exist")
    void saveDoesNot_SaveBusinessFunctionPermission_WhenPermissionDoesNotExist() {

        ResponseEntity<BusinessFunctionPermissionResponse> businessFunctionPermission = testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, createJsonHttpEntityBusinessFucntionPermission(createBusinessFunctionPermissionNotToBeSaved2()), BusinessFunctionPermissionResponse.class);

        Assertions.assertThat(businessFunctionPermission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when already present")
    void doesNotSave_BusinessFunctionPermission_WhenAlreadyPresent() {

        createBusinessFunction();
        createPermission();
        BusinessFunctionPermissionResponse businessFunctionPermission1 = createTemplatePostBusinessFunctionPermission();
        BusinessFunctionPermissionResponse businessFunctionPermission2 = createTemplatePostBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission1.getId()).isEqualTo(businessFunctionPermission2.getId());
        Assertions.assertThat(businessFunctionPermissionRepository.findById(2)).isEmpty();
    }

    private HttpEntity<BusinessFunctionPostRequest> createJsonHttpEntityBusinessFunction(BusinessFunctionPostRequest businessFunctionPostRequest) {
        return new HttpEntity<>(businessFunctionPostRequest, Utility.createJsonHeader());
    }

    private HttpEntity<PermissionPostRequest> createJsonHttpEntityPermission(PermissionPostRequest permissionPostRequest) {
        return new HttpEntity<>(permissionPostRequest, Utility.createJsonHeader());
    }

    private HttpEntity<BusinessFunctionPermissionPostRequest> createJsonHttpEntityBusinessFucntionPermission(BusinessFunctionPermissionPostRequest businessFunctionPermissionPostRequest) {
        return new HttpEntity<>(businessFunctionPermissionPostRequest, Utility.createJsonHeader());
    }

    public BusinessFunctionPermissionResponse createTemplatePostBusinessFunctionPermission() {
        return testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, createJsonHttpEntityBusinessFucntionPermission(createBusinessFunctionPermissionToBeSaved()), BusinessFunctionPermissionResponse.class)
                .getBody();
    }

    private void createBusinessFunction() {
        BusinessFunctionPostRequest businessFunction = Utility.createBusinessFunctionToBeSaved();
        testRestTemplate
                .exchange("/api/v1/business-functions", HttpMethod.POST, createJsonHttpEntityBusinessFunction(businessFunction), BusinessFunctionResponse.class);
    }

    private void createPermission() {
        PermissionPostRequest permission = Utility.createPermissionToBeSaved();
        testRestTemplate
                .exchange("/api/v1/permissions", HttpMethod.POST, createJsonHttpEntityPermission(permission), BusinessFunctionResponse.class);
    }

    public BusinessFunctionPermissionPostRequest createBusinessFunctionPermissionToBeSaved() {
        return BusinessFunctionPermissionPostRequest.builder().businessFunctionId(1).permissionId(1)
                .build();
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