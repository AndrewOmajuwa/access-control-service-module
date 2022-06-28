package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.service.BusinessFunctionPermissionService;
import com.devoteam.accesscontrolservice.util.BusinessFunctionPermissionMapper;
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
        Permission permission = Permission.builder().id(businessFunctionPermissionPostRequest.getPermissionId()).build();

        BusinessFunction businessFunction = BusinessFunction.builder().id(businessFunctionPermissionPostRequest.getBusinessFunctionId()).build();

        BusinessFunctionPermission businessFunctionPermission = BusinessFunctionPermission.builder().permission(permission).businessFunction(businessFunction).build();

        BusinessFunctionPermission savedBusinessFunctionPermission = businessFunctionPermissionService.save(businessFunctionPermission);

        BusinessFunctionPermissionResponse businessFunctionPermissionResponse = BusinessFunctionPermissionMapper.INSTANCE.toBusinessFunctionPermissionResponse(savedBusinessFunctionPermission);

        return ResponseEntity.ok(businessFunctionPermissionResponse);
    }
}
