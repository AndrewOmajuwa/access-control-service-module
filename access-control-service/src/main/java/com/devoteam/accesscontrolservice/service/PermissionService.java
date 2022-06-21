package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PermissionService {
    final private PermissionRepository permissionRepository;

    public Permission save(Permission permission){

        List<Permission> findByName = permissionRepository.findByName(permission.getName());

        return findByName.size() > 0 ? findByName.get(0) : permissionRepository.save(permission);

    }
}
