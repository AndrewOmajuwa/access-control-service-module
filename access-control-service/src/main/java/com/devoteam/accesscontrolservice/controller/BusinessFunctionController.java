package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.service.BusinessFunctionService;
import com.devoteam.accesscontrolservice.util.BusinessFunctionMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "api/v1/business-functions")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BusinessFunctionController {

    private final BusinessFunctionService businessFunctionService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'create')")
    public ResponseEntity<BusinessFunctionResponse> save(@Valid @RequestBody BusinessFunctionPostRequest businessFunctionPostRequest){

        BusinessFunction businessFunction = BusinessFunctionMapper.INSTANCE.toBusinessFunction(businessFunctionPostRequest);

        BusinessFunction savedBusinessFunction = businessFunctionService.save(businessFunction);

        BusinessFunctionResponse businessFunctionResponse = BusinessFunctionMapper.INSTANCE.toBusinessFunctionResponse(savedBusinessFunction);

        return ResponseEntity.ok(businessFunctionResponse);
    }

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'view')")
    public ResponseEntity<Page<BusinessFunction>> getBusinessFunction(Pageable pageable){
        return ResponseEntity.ok(businessFunctionService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'view')")
    public ResponseEntity<BusinessFunction> findById(@PathVariable int id) {
        return ResponseEntity.ok(businessFunctionService.findById(id));
    }

}
