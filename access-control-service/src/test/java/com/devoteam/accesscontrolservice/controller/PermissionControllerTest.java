package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.PermissionPostRequest;
import com.devoteam.accesscontrolservice.domain.PermissionResponse;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
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

import javax.persistence.criteria.CriteriaBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PermissionControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PermissionRepository permissionRepository;

    private Utility utility;

    @Test
    @DisplayName("Save creates Permission when successfull")
    public void save_Permission_WhenSuccessfull(){

        Integer expectedId = 1;
        PermissionResponse permissionResponse = createTemplatePostPermissions();
        Assertions.assertThat(permissionResponse).isNotNull();
        Assertions.assertThat(permissionResponse.getId()).isNotNull();
        Assertions.assertThat(permissionResponse.getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Save does not create Permission when input is blank")
    public void save_DoesNotCreatePermission_WhenInputIsBlank(){

        PermissionResponse permissionResponse = testRestTemplate
                .exchange( "/api/v1/permissions", HttpMethod.POST, createJsonHttpEntity(createPermissionNotToBeSaved()), PermissionResponse.class)
                .getBody();

        Assertions.assertThat(permissionResponse.getId()).isNull();
    }

    @Test
    @DisplayName("Save does not create Permission when already present")
    public void doesNotSave_Permission_WhenAlreadyPresent(){

        PermissionResponse permissionResponse1 = createTemplatePostPermissions();
        PermissionResponse permissionResponse2 = createTemplatePostPermissions();
        Assertions.assertThat(permissionResponse1.getId()).isEqualTo(permissionResponse2.getId());
        Assertions.assertThat(permissionRepository.findById(2)).isEmpty();
    }

    public PermissionResponse createTemplatePostPermissions(){
        return testRestTemplate
                .exchange( "/api/v1/permissions", HttpMethod.POST, createJsonHttpEntity(createPermissionToBeSaved()), PermissionResponse.class)
                .getBody();
    }

    public PermissionPostRequest createPermissionToBeSaved(){
        return PermissionPostRequest.builder()
                .name("View")
                .build();
    }
    public PermissionPostRequest createPermissionNotToBeSaved(){
        return PermissionPostRequest.builder()
                .name("")
                .build();
    }

    private HttpEntity<PermissionPostRequest> createJsonHttpEntity(PermissionPostRequest permissionPostRequest){
        return new HttpEntity<>(permissionPostRequest, utility.createJsonHeader());
    }
}