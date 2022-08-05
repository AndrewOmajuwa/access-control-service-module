package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.keycloak.authorization.client.util.Http;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SetUpService {

    private final UserRepository userRepository;
    private final BusinessFunctionRepository businessFunctionRepository;
    private final PermissionRepository permissionRepository;
    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;
    private final ProfileRepository profileRepository;
    private final ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;
    private final UserProfileRepository userProfileRepository;

    public HttpStatus save(SetUpPostRequest setUpPostRequest){

        Optional<UserKeyCloak> userKeyCloak = userRepository.searchByEmail(setUpPostRequest.getEmail());

        if(userKeyCloak.isEmpty()){
          return HttpStatus.BAD_REQUEST;
        }

        BusinessFunction businessFunction = businessFunctionRepository.findByApplicationNameAndFunctionName(setUpPostRequest.getApplicationName(), setUpPostRequest.getFunctionName()).isEmpty() ? businessFunctionRepository.save(BusinessFunction.builder().applicationName(setUpPostRequest.getApplicationName()).functionName(setUpPostRequest.getFunctionName()).build()) : businessFunctionRepository.findByApplicationNameAndFunctionName(setUpPostRequest.getApplicationName(), setUpPostRequest.getFunctionName()).get();

        Permission permission = permissionRepository.findByName(setUpPostRequest.getPermission()).isEmpty() ? permissionRepository.save(Permission.builder().name(setUpPostRequest.getPermission()).build()) : permissionRepository.findByName(setUpPostRequest.getPermission()).get();

        BusinessFunctionPermission businessFunctionPermission = businessFunctionPermissionRepository.findBusinessFunctionPermission(businessFunction, permission).isEmpty() ? businessFunctionPermissionRepository.save(BusinessFunctionPermission.builder().businessFunction(businessFunction).permission(permission).build()) : businessFunctionPermissionRepository.findBusinessFunctionPermission(businessFunction, permission).get(0);

        Profile profile = profileRepository.findByName(setUpPostRequest.getProfileName()).isEmpty() ? profileRepository.save(Profile.builder().name(setUpPostRequest.getProfileName()).build()) : profileRepository.findByName(setUpPostRequest.getProfileName()).get(0);

        ProfileBusinessFunctionPermission profileBusinessFunctionPermission = profileBusinessFunctionPermissionRepository.findProfileBusinessFunctionPermission(businessFunctionPermission, profile).isEmpty() ? profileBusinessFunctionPermissionRepository.save(ProfileBusinessFunctionPermission.builder().businessFunctionPermission(businessFunctionPermission).profile(profile).build()) : profileBusinessFunctionPermissionRepository.findProfileBusinessFunctionPermission(businessFunctionPermission, profile).get(0);

        UserProfile userProfile = userProfileRepository.findUserProfile(userKeyCloak.get(), profile).isEmpty() ? userProfileRepository.save(UserProfile.builder().userKeyCloak(userKeyCloak.get()).profile(profile).build()) : userProfileRepository.findUserProfile(userKeyCloak.get(), profile).get(0);

        return profileBusinessFunctionPermission == null || userProfile == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
    }
}
