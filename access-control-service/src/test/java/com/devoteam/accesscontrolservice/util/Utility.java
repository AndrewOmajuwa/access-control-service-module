package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.PermissionPostRequest;
import com.devoteam.accesscontrolservice.domain.ProfilePostRequest;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Utility {

    public static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
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
}
