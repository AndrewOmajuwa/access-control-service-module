package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.PermissionPostRequest;
import com.devoteam.accesscontrolservice.domain.PermissionResponse;
import com.devoteam.accesscontrolservice.service.PermissionService;
import com.devoteam.accesscontrolservice.util.PermissionMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/permissions")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'admin', 'create')")
    public ResponseEntity<PermissionResponse> save(@Valid @RequestBody PermissionPostRequest permissionPostRequest){

        Permission permission = PermissionMapper.INSTANCE.toPermission(permissionPostRequest);

        Permission savedPermission = permissionService.save(permission);

        PermissionResponse permissionResponse = PermissionMapper.INSTANCE.toPermissionResponse(savedPermission);

        return ResponseEntity.ok(permissionResponse);
    }
}
