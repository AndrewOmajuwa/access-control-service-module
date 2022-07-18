package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.service.BusinessFunctionService;
import com.devoteam.accesscontrolservice.util.BusinessFunctionMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "api/v1/business-functions")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BusinessFunctionController {

    private final BusinessFunctionService businessFunctionService;

    @GetMapping
    public List<BusinessFunction> findAllBusinessFunctions(){
        return businessFunctionService.listAll();
    }

    @PostMapping
    public ResponseEntity<BusinessFunctionResponse> save(@Valid @RequestBody BusinessFunctionPostRequest businessFunctionPostRequest){

        BusinessFunction businessFunction = BusinessFunctionMapper.INSTANCE.toBusinessFunction(businessFunctionPostRequest);

        BusinessFunction savedBusinessFunction = businessFunctionService.save(businessFunction);

        BusinessFunctionResponse businessFunctionResponse = BusinessFunctionMapper.INSTANCE.toBusinessFunctionResponse(savedBusinessFunction);

        return ResponseEntity.ok(businessFunctionResponse);
    }

}
