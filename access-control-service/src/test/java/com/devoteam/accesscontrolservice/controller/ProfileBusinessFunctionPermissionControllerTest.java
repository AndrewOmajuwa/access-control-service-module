package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileBusinessFunctionPermissionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;
    @Autowired
    Utility utility;


    @Test
    @DisplayName("Save creates Profile Business Function Permission when successfull")
    void save_BusinessFunctionPermission_WhenSuccessfull() {

        utility.createBusinessFunction();
        utility.createPermission();
        utility.createBusinessFunctionPermission();
        utility.createProfile();
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

        utility.createBusinessFunctionPermission();
        utility.createProfile();
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission1 = utility.createProfileBusinessFunctionPermission();
        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermission2 = utility.createProfileBusinessFunctionPermission();
        Assertions.assertThat(profileBusinessFunctionPermission1.getId()).isEqualTo(profileBusinessFunctionPermission2.getId());
        Assertions.assertThat(profileBusinessFunctionPermissionRepository.findById(2)).isEmpty();
    }

    ProfileBusinessFunctionPermissionPostRequest createProfileBusinessFunctionPermissionToBeSaved() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(1).profileId(1)
                .build();
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