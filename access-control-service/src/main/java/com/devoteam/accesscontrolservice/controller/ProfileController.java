package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.domain.ProfilePostRequest;
import com.devoteam.accesscontrolservice.domain.ProfileResponse;
import com.devoteam.accesscontrolservice.service.ProfileService;
import com.devoteam.accesscontrolservice.util.ProfileMapper;
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
@RequestMapping("api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'admin', 'create')")
    public ResponseEntity<ProfileResponse> save(@Valid @RequestBody ProfilePostRequest profilePostRequest){

        Profile profile = ProfileMapper.INSTANCE.toProfile(profilePostRequest);

        Profile savedProfile = profileService.save(profile);

        ProfileResponse profileResponse = ProfileMapper.INSTANCE.toProfileResponse(savedProfile);

        return ResponseEntity.ok(profileResponse);
    }
}
