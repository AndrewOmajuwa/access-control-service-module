package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class Utility {

    private static TestRestTemplate testRestTemplate;

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
                .firstName("Stan")
                .lastName("Marsh")
                .email("StanMarsh@email")
                .build();
    }
    public static ProfilePostRequest createProfileToBeSaved(){
        return ProfilePostRequest.builder()
                .name("Manager")
                .build();
    }

    public static BusinessFunctionPostRequest createBusinessFunctionToBeSaved(){
        return BusinessFunctionPostRequest.builder()
                .applicationName("Doctor-Service")
                .functionName("Doctor")
                .build();
    }
    public static ProfileBusinessFunctionPermissionPostRequest createProfileBusinessFunctionPermissionToBeSaved() {
        return ProfileBusinessFunctionPermissionPostRequest.builder().businessFunctionPermissionId(1).profileId(1)
                .build();
    }
    public static BusinessFunctionPermissionPostRequest createBusinessFunctionPermissionToBeSaved(){

        return BusinessFunctionPermissionPostRequest.builder()
                .permissionId(1)
                .businessFunctionId(1)
                .build();
    }

}
