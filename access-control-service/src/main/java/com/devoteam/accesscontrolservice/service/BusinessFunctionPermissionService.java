package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusinessFunctionPermissionService {

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    final private PermissionService permissionService;
    final private BusinessFunctionService businessFunctionService;

    public BusinessFunctionPermission save(BusinessFunctionPermission businessFunctionPermission) {

        permissionService.findById(businessFunctionPermission.getPermission().getId());

        businessFunctionService.findById(businessFunctionPermission.getBusinessFunction().getId());

        List<BusinessFunctionPermission> ByPermissionAndBusinessFunctionName = businessFunctionPermissionRepository.findBusinessFunctionPermission(businessFunctionPermission.getBusinessFunction(), businessFunctionPermission.getPermission());

        return ByPermissionAndBusinessFunctionName.size() > 0 ? ByPermissionAndBusinessFunctionName.get(0) : businessFunctionPermissionRepository.save(businessFunctionPermission);
    }
}
