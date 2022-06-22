package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermissionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermissionResponse;
import com.devoteam.accesscontrolservice.service.BusinessFunctionPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(value = "api/v1/business-functions-permissions")
@RestController
@RequiredArgsConstructor
public class BusinessFunctionPermissionController {

    private final BusinessFunctionPermissionService businessFunctionPermissionService;

    @PostMapping
    ResponseEntity<BusinessFunctionPermissionResponse> save(@Valid @RequestBody BusinessFunctionPermissionPostRequest businessFunctionPermissionPostRequest){

        BusinessFunctionPermission businessFunctionPermission = BusinessFunctionPermission.builder().permission(businessFunctionPermissionPostRequest.getPermission()).businessFunction(businessFunctionPermissionPostRequest.getBusinessFunction()).build();

        BusinessFunctionPermission savedBusinessFunctionPermission = businessFunctionPermissionService.save(businessFunctionPermission);

        BusinessFunctionPermissionResponse businessFunctionPermissionResponse = BusinessFunctionPermissionResponse.builder().id(savedBusinessFunctionPermission.getId()).build();

        return ResponseEntity.ok(businessFunctionPermissionResponse);
    }
}
