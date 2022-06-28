package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public Permission save(Permission permission){

        List<Permission> findByName = permissionRepository.findByName(permission.getName());

        return !findByName.isEmpty() ? findByName.get(0) : permissionRepository.save(permission);

    }

    public Permission findByIdOrThrowNotFound(Integer id){
        return permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }
}
