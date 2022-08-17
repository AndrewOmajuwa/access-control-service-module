package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import com.devoteam.accesscontrolservice.domain.UserProfile;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final ProfileService profileService;
    private final UserService userService;

    public UserProfile save(UserProfile userProfile) {

        assertProfileExists(userProfile.getProfile().getId());

        assertUserExists(userProfile.getUserKeyCloak().getUuid());

        List<UserProfile> byUserAndProfileName = userProfileRepository.findUserProfile(userProfile.getUserKeyCloak(), userProfile.getProfile());

        return !byUserAndProfileName.isEmpty() ? byUserAndProfileName.get(0) : userProfileRepository.save(userProfile);
    }

    public Page<UserProfile> listAll(Pageable pageable){
        return userProfileRepository.findAll(pageable);
    }

    private void assertProfileExists(Integer id){
        profileService.findByIdOrThrowNotFound(id);
    }
    private void assertUserExists(String uuid){
        userService.findByIdOrThrowNotFound(uuid);
    }
}
