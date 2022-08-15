package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.domain.ProfilePostRequest;
import com.devoteam.accesscontrolservice.domain.ProfileResponse;
import com.devoteam.accesscontrolservice.service.ProfileService;
import com.devoteam.accesscontrolservice.util.ProfileMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'create')")
    public ResponseEntity<ProfileResponse> save(@Valid @RequestBody ProfilePostRequest profilePostRequest){

        Profile profile = ProfileMapper.INSTANCE.toProfile(profilePostRequest);

        Profile savedProfile = profileService.save(profile);

        ProfileResponse profileResponse = ProfileMapper.INSTANCE.toProfileResponse(savedProfile);

        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'view')")
    public ResponseEntity<Page<Profile>> getPermissions(Pageable pageable){
        return ResponseEntity.ok(profileService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'view')")
    public ResponseEntity<Profile> findById(@PathVariable int id) {
        return ResponseEntity.ok(profileService.findById(id));
    }
}
