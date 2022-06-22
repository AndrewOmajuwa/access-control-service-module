package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusinessFunctionPermissionService {

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    final private PermissionRepository permissionRepository;
    final private BusinessFunctionRepository businessFunctionRepository;

    public BusinessFunctionPermission save(BusinessFunctionPermission businessFunctionPermission) {

        permissionRepository.findById(businessFunctionPermission.getBusinessFunction().getId()).orElseThrow(() -> new ResourceNotFoundException("Business Function not found"));
        businessFunctionRepository.findById(businessFunctionPermission.getPermission().getId()).orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
        List<BusinessFunctionPermission> ByPermissionAndBusinessFunctionName = businessFunctionPermissionRepository.findByBusinessFunctionAndPermission(businessFunctionPermission.getBusinessFunction(), businessFunctionPermission.getPermission());
        return ByPermissionAndBusinessFunctionName.size() > 0 ? ByPermissionAndBusinessFunctionName.get(0) : businessFunctionPermissionRepository.save(businessFunctionPermission);
    }
}
