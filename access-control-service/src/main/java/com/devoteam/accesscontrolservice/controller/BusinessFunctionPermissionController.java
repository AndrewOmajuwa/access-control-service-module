package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.requests.post.BusinessFunctionPermissionPostRequest;
import com.devoteam.accesscontrolservice.response.BusinessFunctionPermissionResponse;
import com.devoteam.accesscontrolservice.service.BusinessFunctionPermissionService;
import com.devoteam.accesscontrolservice.util.BusinessFunctionPermissionMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping(value = "api/v1/business-functions-permissions")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BusinessFunctionPermissionController {

    private final BusinessFunctionPermissionService businessFunctionPermissionService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function-permissions', 'create')")
    public ResponseEntity<BusinessFunctionPermissionResponse> save(@Valid @RequestBody BusinessFunctionPermissionPostRequest businessFunctionPermissionPostRequest){
        Permission permission = Permission.builder().id(businessFunctionPermissionPostRequest.getPermissionId()).build();

        BusinessFunction businessFunction = BusinessFunction.builder().id(businessFunctionPermissionPostRequest.getBusinessFunctionId()).build();

        BusinessFunctionPermission businessFunctionPermission = BusinessFunctionPermission.builder().permission(permission).businessFunction(businessFunction).build();

        BusinessFunctionPermission savedBusinessFunctionPermission = businessFunctionPermissionService.save(businessFunctionPermission);

        BusinessFunctionPermissionResponse businessFunctionPermissionResponse = BusinessFunctionPermissionMapper.INSTANCE.toBusinessFunctionPermissionResponse(savedBusinessFunctionPermission);

        return ResponseEntity.ok(businessFunctionPermissionResponse);
    }

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function-permissions', 'view')")
    public ResponseEntity<Page<BusinessFunctionPermission>> getBusinessFunctionPermissions(Pageable pageable, @NotBlank @RequestParam String applicationName){

        return ResponseEntity.ok(businessFunctionPermissionService.listAll(pageable, applicationName));

    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function-permissions', 'delete')")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        businessFunctionPermissionService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(path = "/{id}/cascade")
    @Transactional
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function-permissions', 'cascade-delete')")
    public ResponseEntity<Void> cascadeDelete(@PathVariable int id) {

        businessFunctionPermissionService.cascadeDelete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
