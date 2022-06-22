package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BusinessFunctionPermissionControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    @Test
    @DisplayName("Save creates Business Function Permission when successfull")
    public void save_BusinessFunctionPermission_WhenSuccessfull(){

        createBusinessFunction();
        createPermission();
        Integer expectedId = 1;
        BusinessFunctionPermissionResponse businessFunctionPermission = createTemplatePostBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission).isNotNull();
        Assertions.assertThat(businessFunctionPermission.getId()).isNotNull();
        Assertions.assertThat(businessFunctionPermission.getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Save does not create Business Function Permission when Business Function or Permission does not exist")
    public void saveDoesNot_SaveBusinessFunctionPermission_WhenBusinessFunctionDoesNotExist(){

        BusinessFunctionPermissionResponse businessFunctionPermission = createTemplatePostBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission.getId()).isNull();
    }

    @Test
    @DisplayName("Save does not create Business Function Permission when already present")
    public void doesNotSave_BusinessFunctionPermission_WhenAlreadyPresent(){
        createBusinessFunction();
        createPermission();
        BusinessFunctionPermissionResponse businessFunctionPermission1 = createTemplatePostBusinessFunctionPermission();
        BusinessFunctionPermissionResponse businessFunctionPermission2 = createTemplatePostBusinessFunctionPermission();
        Assertions.assertThat(businessFunctionPermission1.getId()).isEqualTo(businessFunctionPermission2.getId());
        Assertions.assertThat(businessFunctionPermissionRepository.findById(2)).isEmpty();
    }

    public BusinessFunctionPermissionResponse createTemplatePostBusinessFunctionPermission(){
        return testRestTemplate
                .exchange( "/api/v1/business-functions-permissions", HttpMethod.POST, createJsonHttpEntityBusinessFucntionPermission(createBusinessFunctionPermissionToBeSaved()), BusinessFunctionPermissionResponse.class)
                .getBody();
    }

    public BusinessFunctionPermissionPostRequest createBusinessFunctionPermissionToBeSaved(){
        BusinessFunction businessFunction = BusinessFunction.builder().functionName("doctor").applicationName("service").id(1).build();
        Permission permission = Permission.builder().name("view").id(1).build();
        return BusinessFunctionPermissionPostRequest.builder().businessFunction(businessFunction).permission(permission)
                .build();
    }

    private HttpEntity<BusinessFunctionPermissionPostRequest> createJsonHttpEntityBusinessFucntionPermission(BusinessFunctionPermissionPostRequest businessFunctionPermissionPostRequest){
        return new HttpEntity<>(businessFunctionPermissionPostRequest, createJsonHeader());
    }
    private void createBusinessFunction(){
        BusinessFunctionPostRequest businessFunction = BusinessFunctionPostRequest.builder()
                .applicationName("Doctor-Service")
                .functionName("Doctor")
                .build();
        testRestTemplate
                .exchange( "/api/v1/business-functions", HttpMethod.POST, createJsonHttpEntityBusinessFunction(businessFunction), BusinessFunctionResponse.class)
                .getBody();
    }

    private HttpEntity<BusinessFunctionPostRequest> createJsonHttpEntityBusinessFunction(BusinessFunctionPostRequest businessFunctionPostRequest){
        return new HttpEntity<>(businessFunctionPostRequest, createJsonHeader());
    }

    private void createPermission(){
        PermissionPostRequest permission = PermissionPostRequest.builder()
                .name("View")
                .build();
        testRestTemplate
                .exchange( "/api/v1/permissions", HttpMethod.POST, createJsonHttpEntityPermission(permission), BusinessFunctionResponse.class)
                .getBody();
    }

    private HttpEntity<PermissionPostRequest> createJsonHttpEntityPermission(PermissionPostRequest permissionPostRequest){
        return new HttpEntity<>(permissionPostRequest, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}