package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.service.BusinessFunctionPermissionService;
import com.devoteam.accesscontrolservice.service.UserProfileService;
import com.devoteam.accesscontrolservice.util.BusinessFunctionPermissionMapper;
import com.devoteam.accesscontrolservice.util.UserProfileMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(value = "api/v1/user-profiles")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'user-profiles', 'create')")
    public ResponseEntity<UserProfileResponse> save(@Valid @RequestBody UserProfilePostRequest userProfilePostRequest){
        Profile profile = Profile.builder().id(userProfilePostRequest.getProfileId()).build();

        UserKeyCloak userKeyCloak = UserKeyCloak.builder().uuid(userProfilePostRequest.getUserKeyCloakId()).build();

        UserProfile userProfile= UserProfile.builder().profile(profile).userKeyCloak(userKeyCloak).build();

        UserProfile savedUserProfile = userProfileService.save(userProfile);

        UserProfileResponse userProfileResponse = UserProfileMapper.INSTANCE.toUserProfileResponse(savedUserProfile);

        return ResponseEntity.ok(userProfileResponse);
    }
}
