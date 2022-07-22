package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.ValidateAccessPostRequest;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.service.BusinessFunctionService;
import com.devoteam.accesscontrolservice.service.PermissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/validate-access")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ValidateAccessController {

    private final ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;

    private final BusinessFunctionService businessFunctionService;

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<HttpStatus> validateAccess(@Valid @RequestBody ValidateAccessPostRequest validateAccessPostRequest){

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        businessFunctionService.findByApplicationAndFunctionNamesOrThrowNotFound(validateAccessPostRequest.getApplicationName(), validateAccessPostRequest.getFunctionName());

        permissionService.findByPermissionNameOrThrowNotFound(validateAccessPostRequest.getPermission());

        boolean isAccessAllowed = profileBusinessFunctionPermissionRepository.isAccessAllowed(uuid, validateAccessPostRequest.getApplicationName(), validateAccessPostRequest.getFunctionName(), validateAccessPostRequest.getPermission());

        return isAccessAllowed ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
