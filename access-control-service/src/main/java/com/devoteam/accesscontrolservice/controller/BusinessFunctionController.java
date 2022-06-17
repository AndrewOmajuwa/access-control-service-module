package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.service.BusinessFunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "api/v1/business-functions")
@RestController
@RequiredArgsConstructor
public class BusinessFunctionController {

    private final BusinessFunctionService businessFunctionService;

    @GetMapping
    public List<BusinessFunction> findAllBusinessFunctions(){
        return businessFunctionService.listAll();
    }

    @PostMapping
    ResponseEntity<BusinessFunctionResponse> save(@Valid @RequestBody BusinessFunctionPostRequest businessFunctionPostRequest){

        BusinessFunction businessFunction = BusinessFunction.builder()
                .functionName(businessFunctionPostRequest.getFunctionName())
                .applicationName(businessFunctionPostRequest.getApplicationName())
                .build();

        BusinessFunction savedBusinessFunction = businessFunctionService.save(businessFunction);

        BusinessFunctionResponse businessFunctionResponse = BusinessFunctionResponse.builder().id(savedBusinessFunction.getId()).build();

        return ResponseEntity.ok(businessFunctionResponse);
    }

}
