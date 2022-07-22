package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;

import java.util.UUID;

public class Utility {

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
                .name("View")
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
                .name("View")
                .build();
    }

    public static BusinessFunctionPostRequest createBusinessFunctionToBeSaved(){
        return BusinessFunctionPostRequest.builder()
                .applicationName("Doctor-Service")
                .functionName("Doctor")
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
    public static UserProfilePostRequest createUserProfileToBeSaved(UUID uuid) {

        return UserProfilePostRequest.builder().userKeyCloakId(uuid).profileId(1)
                .build();
    }
    public static HttpEntity<UserPostRequest> createUserJsonHttpEntity(UserPostRequest userPostRequest){
        return new HttpEntity<>(userPostRequest, Utility.createJsonHeader());
    }


}
