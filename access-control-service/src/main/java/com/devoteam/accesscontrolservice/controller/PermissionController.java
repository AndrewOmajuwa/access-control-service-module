package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.PermissionPostRequest;
import com.devoteam.accesscontrolservice.domain.PermissionResponse;
import com.devoteam.accesscontrolservice.service.PermissionService;
import com.devoteam.accesscontrolservice.util.PermissionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/permissions")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'permission', 'create')")
    public ResponseEntity<PermissionResponse> save(@Valid @RequestBody PermissionPostRequest permissionPostRequest){

        Permission permission = PermissionMapper.INSTANCE.toPermission(permissionPostRequest);

        Permission savedPermission = permissionService.save(permission);

        PermissionResponse permissionResponse = PermissionMapper.INSTANCE.toPermissionResponse(savedPermission);

        return ResponseEntity.ok(permissionResponse);
    }

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'permission', 'view')")
    public ResponseEntity<Page<Permission>> getPermissions(Pageable pageable){
        return ResponseEntity.ok(permissionService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'permission', 'view')")
    public ResponseEntity<Permission> findById(@PathVariable int id) {
        return ResponseEntity.ok(permissionService.findById(id));
    }

    @PutMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'permission', 'update')")
    public ResponseEntity<Void> update(@RequestBody @NotBlank(message = "Please insert a valid permission name") Permission permission) {

        permissionService.update(permission);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
