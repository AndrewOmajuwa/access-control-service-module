package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.ProfileBusinessFunctionPermission;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.AssertionsUtil;
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

    private final AssertionsUtil assertionsUtil;

    public ProfileBusinessFunctionPermission save(ProfileBusinessFunctionPermission profileBusinessFunctionPermission) {

        assertProfileExists(profileBusinessFunctionPermission.getProfile().getId());

        assertionsUtil.assertBusinessFunctionPermissionExists(profileBusinessFunctionPermission.getBusinessFunctionPermission().getId());

        List<ProfileBusinessFunctionPermission> byProfileAndBusinessFunctionPermissionName = profileBusinessFunctionPermissionRepository.findProfileBusinessFunctionPermission(profileBusinessFunctionPermission.getBusinessFunctionPermission(), profileBusinessFunctionPermission.getProfile());

        return !byProfileAndBusinessFunctionPermissionName.isEmpty() ? byProfileAndBusinessFunctionPermissionName.get(0) : profileBusinessFunctionPermissionRepository.save(profileBusinessFunctionPermission);
    }

    public Page<ProfileBusinessFunctionPermission> listAll(Pageable pageable, String profileName){

        return profileBusinessFunctionPermissionRepository.findBusinessFunctionPermissionByProfileName(profileName, pageable);

    }

    public void delete(Integer id){

        ProfileBusinessFunctionPermission profileBusinessFunctionPermission = findByIdOrThrowNotFound(id);

        profileBusinessFunctionPermissionRepository.delete(profileBusinessFunctionPermission);
    }

    public ProfileBusinessFunctionPermission findByIdOrThrowNotFound(Integer id){
        return profileBusinessFunctionPermissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile Business Function Permission was not found"));
    }

    public void deleteByBusinessFunctionPermissionIds(List<Integer> ids){
        profileBusinessFunctionPermissionRepository.deleteProfileBusinessFunctionPermissionByBusinessFunctionPermissionId(ids);
    }

    private void assertProfileExists(Integer id){
        profileService.findByIdOrThrowNotFound(id);
    }
}
