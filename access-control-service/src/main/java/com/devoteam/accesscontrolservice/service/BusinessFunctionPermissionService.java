package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusinessFunctionPermissionService {

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;
    private final PermissionService permissionService;

    public BusinessFunctionPermission save(BusinessFunctionPermission businessFunctionPermission) {

        assertPermissionExists(businessFunctionPermission.getPermission().getId());

        assertBusinessFunctionExists(businessFunctionPermission.getBusinessFunction().getId());

        List<BusinessFunctionPermission> byPermissionAndBusinessFunctionName = businessFunctionPermissionRepository.findBusinessFunctionPermission(businessFunctionPermission.getBusinessFunction(), businessFunctionPermission.getPermission());

        return !byPermissionAndBusinessFunctionName.isEmpty() ? byPermissionAndBusinessFunctionName.get(0) : businessFunctionPermissionRepository.save(businessFunctionPermission);
    }

    public BusinessFunctionPermission findByIdOrThrowNotFound(Integer id){
        return businessFunctionPermissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business Function Permission was not found"));
    }

    public Page<BusinessFunctionPermission> listAll(Pageable pageable, String applicationName){

        return businessFunctionPermissionRepository.findBusinessFunctionPermissionByApplicationName(applicationName, pageable);

    }

    public void delete(BusinessFunctionPermission businessFunctionPermission){

        businessFunctionPermissionRepository.delete(businessFunctionPermission);

    }

    public List<BusinessFunctionPermission> listBusinessFunctionPermissionByBusinessFunctionId(Integer id){
        return businessFunctionPermissionRepository.listBusinessFunctionPermissionByBusinessFunctionId(id);
    }

    private void assertPermissionExists(Integer id){
        permissionService.findByIdOrThrowNotFound(id);
    }
    private void assertBusinessFunctionExists(Integer id){
        businessFunctionPermissionRepository.findBusinessFunctionById(id).orElseThrow(() -> new ResourceNotFoundException("Business Function was not found"));
    }
}
