package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;

import java.util.UUID;

@Configuration
public class Utility {

    @Autowired
    public TestRestTemplate testRestTemplate;

    public static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    public static <T> HttpEntity<T> createJsonHttpEntity(T t){
        return new HttpEntity<>(t, Utility.createJsonHeader());
    }


    public static PermissionPostRequest createPermissionToBeSaved(){
        return PermissionPostRequest.builder()
                .name("create2")
                .build();
    }
    public static UserPostRequest createUserKeycloakToBeSaved(){
        return UserPostRequest.builder()
                .firstName("Eric")
                .lastName("Cartman")
                .email("eric.cartman@email.com")
                .password("password")
                .build();
    }
    public static ProfilePostRequest createProfileToBeSaved(){
        return ProfilePostRequest.builder()
                .name("view")
                .build();
    }

    public static BusinessFunctionPostRequest createBusinessFunctionToBeSaved(){
        return BusinessFunctionPostRequest.builder()
                .applicationName("access-control-service")
                .functionName("admin")
                .build();
    }
    public static BusinessFunctionPermissionPostRequest createBusinessFunctionPermissionToBeSaved(){

        return BusinessFunctionPermissionPostRequest.builder()
                .permissionId(1)
                .businessFunctionId(1)
                .build();
    }
    public static ProfileBusinessFunctionPermissionPostRequest createProfileBusinessFunctionPermissionToBeSaved() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(1).profileId(1)
                .build();
    }
    public static UserProfilePostRequest createUserProfileToBeSaved(String uuid) {

        return UserProfilePostRequest.builder().userKeyCloakId(uuid).profileId(1)
                .build();
    }
    public static HttpEntity<UserPostRequest> createUserJsonHttpEntity(UserPostRequest userPostRequest){
        return new HttpEntity<>(userPostRequest, Utility.createJsonHeader());
    }


    public UserResponse createUser() {
        UserPostRequest user = Utility.createUserKeycloakToBeSaved();
        return testRestTemplate
                .exchange("/api/v1/users", HttpMethod.POST, Utility.createUserJsonHttpEntity(user), UserResponse.class).getBody();
    }

    public ProfileResponse createProfile() {
        ProfilePostRequest profile = Utility.createProfileToBeSaved();
        return testRestTemplate
                .exchange("/api/v1/profiles", HttpMethod.POST, Utility.createJsonHttpEntity(profile), ProfileResponse.class).getBody();
    }

    public BusinessFunctionResponse createBusinessFunction() {
        BusinessFunctionPostRequest businessFunction = Utility.createBusinessFunctionToBeSaved();
        return testRestTemplate
                .exchange("/api/v1/business-functions", HttpMethod.POST, Utility.createJsonHttpEntity(businessFunction), BusinessFunctionResponse.class).getBody();
    }

    public PermissionResponse createPermission() {
        PermissionPostRequest permission = Utility.createPermissionToBeSaved();

        return testRestTemplate
                .exchange("/api/v1/permissions", HttpMethod.POST, Utility.createJsonHttpEntity(permission), PermissionResponse.class).getBody();
    }
    public BusinessFunctionPermissionResponse createBusinessFunctionPermission() {
        BusinessFunctionPermissionPostRequest businessFunctionPermission = Utility.createBusinessFunctionPermissionToBeSaved();
        return testRestTemplate
                .exchange("/api/v1/business-functions-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(businessFunctionPermission), BusinessFunctionPermissionResponse.class).getBody();
    }
    public ProfileBusinessFunctionPermissionResponse createProfileBusinessFunctionPermission() {
        return testRestTemplate
                .exchange("/api/v1/profile-business-function-permissions", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createProfileBusinessFunctionPermissionToBeSaved()), ProfileBusinessFunctionPermissionResponse.class).getBody();
    }

    public UserProfileResponse createUserProfile(String uuid) {
        return testRestTemplate
                .exchange("/api/v1/user-profiles", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createUserProfileToBeSaved(uuid)), UserProfileResponse.class).getBody();
    }

}
