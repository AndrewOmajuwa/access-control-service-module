package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
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
class BusinessFunctionResponseControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BusinessFunctionRepository businessFunctionRepository;

    @Test
    @DisplayName("Save creates Business Function when successfull")
    public void save_BusinessFunction_WhenSuccessfull(){

        Integer expectedId = 1;
        BusinessFunctionResponse businessFunctionResponse = testRestTemplate.withBasicAuth("andrew", "devoteam").exchange( "/api/v1/business-functions", HttpMethod.POST, createJsonHttpEntity(createBusinessFunctionToBeSaved()), BusinessFunctionResponse.class).getBody();
        Assertions.assertThat(businessFunctionResponse).isNotNull();
        Assertions.assertThat(businessFunctionResponse.getId()).isNotNull();
        Assertions.assertThat(businessFunctionResponse.getId()).isEqualTo(expectedId);

    }
    @Test
    @DisplayName("Save does not create Business Function when already present")
    public void doesNotSave_BusinessFunction_WhenAlreadyPresent(){

        BusinessFunctionResponse businessFunctionResponse1 = testRestTemplate.withBasicAuth("andrew", "devoteam").exchange( "/api/v1/business-functions", HttpMethod.POST, createJsonHttpEntity(createBusinessFunctionToBeSaved()), BusinessFunctionResponse.class).getBody();
        BusinessFunctionResponse businessFunctionResponse2 = testRestTemplate.withBasicAuth("andrew", "devoteam").exchange( "/api/v1/business-functions", HttpMethod.POST, createJsonHttpEntity(createBusinessFunctionToBeSaved()), BusinessFunctionResponse.class).getBody();
        Assertions.assertThat(businessFunctionResponse2.getFunctionName()).isEqualTo(businessFunctionResponse1.getFunctionName());
        Assertions.assertThat(businessFunctionResponse2.getApplicationName()).isEqualTo(businessFunctionResponse1.getApplicationName());
        Assertions.assertThat(businessFunctionRepository.findById(2)).isEmpty();
    }

    public BusinessFunctionResponse createBusinessFunctionToBeSaved(){
        return BusinessFunctionResponse.builder()
                .functionName("Doctor")
                .applicationName("Doctor-Service")
                .build();
    }

    private HttpEntity<BusinessFunctionResponse> createJsonHttpEntity(BusinessFunctionResponse businessFunctionResponse){
        return new HttpEntity<>(businessFunctionResponse, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}