package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionRequest;
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
    public List<BusinessFunctionResponse> findAllBusinessFunctions(){
        return businessFunctionService.listAll();
    }

    @PostMapping
    ResponseEntity<BusinessFunctionResponse> save(@Valid @RequestBody BusinessFunctionRequest businessFunctionRequest){

        BusinessFunctionResponse businessFunctionResponse = BusinessFunctionResponse.builder().functionName(businessFunctionRequest.getFunctionName()).applicationName(businessFunctionRequest.getApplicationName()).build();

        return ResponseEntity.ok(businessFunctionService.save(businessFunctionResponse));
    }

}
