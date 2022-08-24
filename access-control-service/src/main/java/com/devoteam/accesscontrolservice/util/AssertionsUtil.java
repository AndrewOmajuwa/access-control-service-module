package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AssertionsUtil {

    private final BusinessFunctionRepository businessFunctionRepository;

    private final PermissionRepository permissionRepository;

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    private final ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;

    private final UserProfileRepository userProfileRepository;

    public void assertBusinessFunctionExists(Integer id){

        businessFunctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function does not exist"));

    }

    public void assertPermissionExists(Integer id){

        permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function does not exist"));

    }
    public void assertBusinessFunctionPermissionExists(Integer id){

        businessFunctionPermissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function permission does not exist"));

    }

    public void assertPermissionIsNotAssociatedWithBusinessFunction(Integer id) {

        if(!businessFunctionPermissionRepository.findBusinessFunctionPermissionByPermissionId(id).isEmpty()){

            throw new BadRequest("Cannot delete Permission associated with Business Function");

        }
    }

    public void assertBusinessFunctionPermissionIsNotAssociatedWithProfile(Integer id) {

        if(!profileBusinessFunctionPermissionRepository.findProfileBusinessFunctionPermissionByBusinessFunctionPermissionId(id).isEmpty()){

            throw new BadRequest("Cannot delete Business Function Permission associated with Profile");

        }
    }

    public void assertProfileIsNotAssociatedWithProfileBusinessFunctionPermission(Integer id) {

        if(!profileBusinessFunctionPermissionRepository.findProfileBusinessFunctionPermissionByProfileId(id).isEmpty()){

            throw new BadRequest("Cannot delete Profile associated with Profile Business Function Permission");

        }
    }

    public void assertProfileIsNotAssociatedWithUserProfile(Integer id) {

        if(!userProfileRepository.findUserProfileByProfileId(id).isEmpty()){

            throw new BadRequest("Cannot delete Profile associated with User Profile");

        }
    }
}
