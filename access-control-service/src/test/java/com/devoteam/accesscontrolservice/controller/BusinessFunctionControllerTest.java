package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import com.devoteam.accesscontrolservice.util.BusinessFunctionMapper;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BusinessFunctionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BusinessFunctionRepository businessFunctionRepository;
    @Autowired
    private Utility utility;

    @Test
    @DisplayName("Save creates Business Function when successfull")
    public void save_BusinessFunction_WhenSuccessfull(){
        Integer expectedId = 1;
        BusinessFunctionResponse businessFunctionResponse = utility.createBusinessFunction();
        Assertions.assertThat(businessFunctionResponse).isNotNull();
        Assertions.assertThat(businessFunctionResponse.getId()).isNotNull();
        Assertions.assertThat(businessFunctionResponse.getId()).isEqualTo(expectedId);

    }

    @Test
    @DisplayName("Save does not create Business Function when already present")
    public void doesNotSave_BusinessFunction_WhenAlreadyPresent(){

        BusinessFunctionResponse businessFunction1 = utility.createBusinessFunction();
        BusinessFunctionResponse businessFunction2 = utility.createBusinessFunction();
        Assertions.assertThat(businessFunction1.getId()).isEqualTo(businessFunction2.getId());
        Assertions.assertThat(businessFunctionRepository.findById(2)).isEmpty();
    }

}