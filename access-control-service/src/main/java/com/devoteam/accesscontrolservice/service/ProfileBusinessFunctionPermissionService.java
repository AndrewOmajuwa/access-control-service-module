package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.ProfileBusinessFunctionPermission;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfileBusinessFunctionPermissionService {

    private final ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;
    private final ProfileService profileService;
    private final BusinessFunctionPermissionService businessFunctionPermissionService;

    public ProfileBusinessFunctionPermission save(ProfileBusinessFunctionPermission profileBusinessFunctionPermission) {

        assertProfileExists(profileBusinessFunctionPermission.getProfile().getId());

        assertBusinessFunctionPermissionExists(profileBusinessFunctionPermission.getBusinessFunctionPermission().getId());

        List<ProfileBusinessFunctionPermission> byProfileAndBusinessFunctionPermissionName = profileBusinessFunctionPermissionRepository.findProfileBusinessFunctionPermission(profileBusinessFunctionPermission.getBusinessFunctionPermission(), profileBusinessFunctionPermission.getProfile());

        return !byProfileAndBusinessFunctionPermissionName.isEmpty() ? byProfileAndBusinessFunctionPermissionName.get(0) : profileBusinessFunctionPermissionRepository.save(profileBusinessFunctionPermission);
    }

    public Page<ProfileBusinessFunctionPermission> listAll(Pageable pageable, String profileName){

        return profileBusinessFunctionPermissionRepository.findBusinessFunctionPermissionByProfileName(profileName, pageable);

    }

    public void deleteAll(List<ProfileBusinessFunctionPermission> profileBusinessFunctionPermissions){

        profileBusinessFunctionPermissionRepository.deleteAll(profileBusinessFunctionPermissions);

    }

    public List<ProfileBusinessFunctionPermission> listProfileBusinessFunctionPermissionByBusinessFunctionPermissionId(Integer id){
        return profileBusinessFunctionPermissionRepository.listProfileBusinessFunctionPermissionByBusinessFunctionPermissionId(id);
    }

    private void assertProfileExists(Integer id){
        profileService.findByIdOrThrowNotFound(id);
    }
    private void assertBusinessFunctionPermissionExists(Integer id){
        businessFunctionPermissionService.findByIdOrThrowNotFound(id);
    }
}
