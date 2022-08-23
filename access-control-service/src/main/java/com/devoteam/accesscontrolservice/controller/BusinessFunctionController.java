package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.requests.post.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.requests.put.BusinessFunctionPutRequest;
import com.devoteam.accesscontrolservice.response.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.service.BusinessFunctionService;
import com.devoteam.accesscontrolservice.util.BusinessFunctionMapper;
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

    @PutMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'update')")
    public ResponseEntity<Void> update(@RequestBody @Valid BusinessFunctionPutRequest businessFunctionPutRequest) {

        BusinessFunction businessFunction = BusinessFunctionMapper.INSTANCE.toBusinessFunction(businessFunctionPutRequest);

        businessFunctionService.update(businessFunction);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'delete')")
    public ResponseEntity<Void> delete(@PathVariable @NotBlank int id) {

        businessFunctionService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(path = "/{id}/cascade")
    @Transactional
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'cascade-delete')")
    public ResponseEntity<Void> cascadeDelete(@PathVariable int id) {

        businessFunctionService.cascadeDelete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
