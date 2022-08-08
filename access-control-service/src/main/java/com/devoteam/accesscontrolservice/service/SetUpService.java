package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SetUpService {

    private final UserRepository userRepository;
    private final BusinessFunctionService businessFunctionService;
    private final PermissionService permissionService;
    private final BusinessFunctionPermissionService businessFunctionPermissionService;
    private final ProfileService profileService;
    private final ProfileBusinessFunctionPermissionService profileBusinessFunctionPermissionService;
    private final UserProfileService userProfileService;

    public HttpStatus save(SetUpPostRequest setUpPostRequest){

        Optional<UserKeyCloak> userKeyCloak = getUserKeyCloak(setUpPostRequest);

        if (userKeyCloak.isEmpty()) return HttpStatus.BAD_REQUEST;

        ProfileBusinessFunctionPermission profileBusinessFunctionPermission = getProfileBusinessFunctionPermission(setUpPostRequest);

        UserProfile userProfile = userProfileService.save(UserProfile.builder().profile(profileBusinessFunctionPermission.getProfile()).userKeyCloak(userKeyCloak.get()).build());

        return userProfile == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
    }

    private Optional<UserKeyCloak> getUserKeyCloak(SetUpPostRequest setUpPostRequest) {

        return userRepository.searchByEmail(setUpPostRequest.getEmail());
    }

    private BusinessFunctionPermission getBusinessFunctionPermission(SetUpPostRequest setUpPostRequest) {

        BusinessFunction businessFunction = businessFunctionService.save(BusinessFunction.builder().applicationName(setUpPostRequest.getApplicationName()).functionName(setUpPostRequest.getFunctionName()).build());

        Permission permission = permissionService.save(Permission.builder().name(setUpPostRequest.getPermission()).build());

        return businessFunctionPermissionService.save(BusinessFunctionPermission.builder().businessFunction(businessFunction).permission(permission).build());
    }

    private ProfileBusinessFunctionPermission getProfileBusinessFunctionPermission(SetUpPostRequest setUpPostRequest) {

        BusinessFunctionPermission businessFunctionPermission = getBusinessFunctionPermission(setUpPostRequest);

        Profile profile = profileService.save(Profile.builder().name(setUpPostRequest.getProfileName()).build());

        return profileBusinessFunctionPermissionService.save(ProfileBusinessFunctionPermission.builder().profile(profile).businessFunctionPermission(businessFunctionPermission).build());
    }
}
