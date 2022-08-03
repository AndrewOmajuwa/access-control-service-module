package com.devoteam.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@Component
public class CheckPermissionService {

    private RestTemplate restTemplate;

    @Autowired
    public void getRestTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public boolean validateAccess(String applicationName, String functionName, String permission){

        ValidateAccessPostRequest validateAccessPostRequest = ValidateAccessPostRequest.builder()
                .applicationName(applicationName)
                .functionName(functionName)
                .permission(permission)
                .build();

        HttpStatus httpStatus = restTemplate.exchange("http://localhost:8090/api/v1/validate-access", HttpMethod.POST, createJsonHttpEntity(validateAccessPostRequest), HttpStatus.class).getBody();

        return httpStatus == HttpStatus.OK;

    }

    public static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    public static <T> HttpEntity<T> createJsonHttpEntity(T t){
        return new HttpEntity<>(t, createJsonHeader());
    }

}
