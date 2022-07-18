package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusinessFunctionPermissionService {

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;
    private final PermissionService permissionService;
    private final BusinessFunctionService businessFunctionService;

    public BusinessFunctionPermission save(BusinessFunctionPermission businessFunctionPermission) {

        assertPermissionExists(businessFunctionPermission.getPermission().getId());

        assertBusinessFunctionExists(businessFunctionPermission.getBusinessFunction().getId());

        List<BusinessFunctionPermission> byPermissionAndBusinessFunctionName = businessFunctionPermissionRepository.findBusinessFunctionPermission(businessFunctionPermission.getBusinessFunction(), businessFunctionPermission.getPermission());

        return !byPermissionAndBusinessFunctionName.isEmpty() ? byPermissionAndBusinessFunctionName.get(0) : businessFunctionPermissionRepository.save(businessFunctionPermission);
    }

    public BusinessFunctionPermission findByIdOrThrowNotFound(Integer id){
        return businessFunctionPermissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business Function Permission was not found"));
    }

    private void assertPermissionExists(Integer id){
        permissionService.findByIdOrThrowNotFound(id);
    }
    private void assertBusinessFunctionExists(Integer id){
        businessFunctionService.findByIdOrThrowNotFound(id);
    }
}
