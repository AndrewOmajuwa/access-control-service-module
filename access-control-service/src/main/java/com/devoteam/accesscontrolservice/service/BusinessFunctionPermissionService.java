package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.util.AssertionsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusinessFunctionPermissionService {

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    private final ProfileBusinessFunctionPermissionService profileBusinessFunctionPermissionService;

    private final PermissionService permissionService;

    private final AssertionsUtil assertionsUtil;

    public BusinessFunctionPermission save(BusinessFunctionPermission businessFunctionPermission) {

        assertPermissionExists(businessFunctionPermission.getPermission().getId());

        assertionsUtil.assertBusinessFunctionExists(businessFunctionPermission.getBusinessFunction().getId());

        List<BusinessFunctionPermission> byPermissionAndBusinessFunctionName = businessFunctionPermissionRepository.findBusinessFunctionPermission(businessFunctionPermission.getBusinessFunction(), businessFunctionPermission.getPermission());

        return !byPermissionAndBusinessFunctionName.isEmpty() ? byPermissionAndBusinessFunctionName.get(0) : businessFunctionPermissionRepository.save(businessFunctionPermission);
    }

    public Page<BusinessFunctionPermission> listAll(Pageable pageable, String applicationName){

        return businessFunctionPermissionRepository.findBusinessFunctionPermissionByApplicationName(applicationName, pageable);

    }

    @Transactional
    public void deleteBasedOnBusinessFunctionId(Integer id){

        List<Integer> businessFunctionPermissionIds = businessFunctionPermissionRepository.listBusinessFunctionPermissionByBusinessFunctionId(id).stream().map(BusinessFunctionPermission::getId).toList();

        profileBusinessFunctionPermissionService.deleteByBusinessFunctionPermissionIds(businessFunctionPermissionIds);

        businessFunctionPermissionRepository.deleteBusinessFunctionPermissionByBusinessFunctionId(id);
    }

    private void assertPermissionExists(Integer id){
        permissionService.findByIdOrThrowNotFound(id);
    }
}
