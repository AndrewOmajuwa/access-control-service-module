package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission save(Permission permission){

        Optional<Permission> findByName = permissionRepository.findByName(permission.getName());

        return findByName.orElseGet(() -> permissionRepository.save(permission));

    }

    public Page<Permission> listAll(Pageable pageable){
        return permissionRepository.findAll(pageable);
    }

    public Permission findByIdOrThrowNotFound(Integer id){
        return permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }
    public Permission findByPermissionNameOrThrowNotFound(String permission){
        return permissionRepository.findByName(permission).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }
}
