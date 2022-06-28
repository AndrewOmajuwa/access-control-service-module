package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import com.devoteam.accesscontrolservice.util.BusinessFunctionMapper;
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
class BusinessFunctionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BusinessFunctionRepository businessFunctionRepository;

    @Test
    @DisplayName("Save creates Business Function when successfull")
    public void save_BusinessFunction_WhenSuccessfull(){
        Integer expectedId = 1;
        BusinessFunctionResponse businessFunctionResponse = createTemplatePostBusinessFunction();
        Assertions.assertThat(businessFunctionResponse).isNotNull();
        Assertions.assertThat(businessFunctionResponse.getId()).isNotNull();
        Assertions.assertThat(businessFunctionResponse.getId()).isEqualTo(expectedId);

    }

    @Test
    @DisplayName("Save does not create Business Function when already present")
    public void doesNotSave_BusinessFunction_WhenAlreadyPresent(){

        BusinessFunctionResponse businessFunction1 = createTemplatePostBusinessFunction();
        BusinessFunctionResponse businessFunction2 = createTemplatePostBusinessFunction();
        Assertions.assertThat(businessFunction1.getId()).isEqualTo(businessFunction2.getId());
        Assertions.assertThat(businessFunctionRepository.findById(2)).isEmpty();
    }

    public BusinessFunctionResponse createTemplatePostBusinessFunction(){
        return testRestTemplate
                .exchange( "/api/v1/business-functions", HttpMethod.POST, createJsonHttpEntity(createBusinessFunctionToBeSaved()), BusinessFunctionResponse.class)
                .getBody();
    }

    public BusinessFunctionPostRequest createBusinessFunctionToBeSaved(){
        return BusinessFunctionPostRequest.builder()
                .applicationName("Doctor-Service")
                .functionName("Doctor")
                .build();
    }

    private HttpEntity<BusinessFunctionPostRequest> createJsonHttpEntity(BusinessFunctionPostRequest businessFunctionPostRequest){
        return new HttpEntity<>(businessFunctionPostRequest, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}