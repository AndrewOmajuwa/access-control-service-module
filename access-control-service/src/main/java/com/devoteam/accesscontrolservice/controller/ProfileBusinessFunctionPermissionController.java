package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.requests.post.ProfileBusinessFunctionPermissionPostRequest;
import com.devoteam.accesscontrolservice.response.ProfileBusinessFunctionPermissionResponse;
import com.devoteam.accesscontrolservice.service.ProfileBusinessFunctionPermissionService;
import com.devoteam.accesscontrolservice.util.ProfileBusinessFunctionPermissionMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping(value = "api/v1/profile-business-function-permissions")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileBusinessFunctionPermissionController {

    private final ProfileBusinessFunctionPermissionService profileBusinessFunctionPermissionService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile-business-function-permissions', 'create')")
    public ResponseEntity<ProfileBusinessFunctionPermissionResponse> save(@Valid @RequestBody ProfileBusinessFunctionPermissionPostRequest profileBusinessFunctionPermissionPostRequest){
        Profile profile = Profile.builder().id(profileBusinessFunctionPermissionPostRequest.getProfileId()).build();

        BusinessFunctionPermission businessFunctionPermission = BusinessFunctionPermission.builder().id(profileBusinessFunctionPermissionPostRequest.getBusinessFunctionPermissionId()).build();

        ProfileBusinessFunctionPermission profileBusinessFunctionPermission = ProfileBusinessFunctionPermission.builder().profile(profile).businessFunctionPermission(businessFunctionPermission).build();

        ProfileBusinessFunctionPermission savedBusinessFunctionPermission = profileBusinessFunctionPermissionService.save(profileBusinessFunctionPermission);

        ProfileBusinessFunctionPermissionResponse profileBusinessFunctionPermissionResponse = ProfileBusinessFunctionPermissionMapper.INSTANCE.toProfileBusinessFunctionPermissionResponse(savedBusinessFunctionPermission);

        return ResponseEntity.ok(profileBusinessFunctionPermissionResponse);
    }

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile-business-function-permissions', 'view')")
    public ResponseEntity<Page<ProfileBusinessFunctionPermission>> getProfileBusinessFunctionPermissions(Pageable pageable, @NotBlank @RequestParam String profileName){

        return ResponseEntity.ok(profileBusinessFunctionPermissionService.listAll(pageable, profileName));

    }
}
