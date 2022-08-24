package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
import com.devoteam.accesscontrolservice.util.AssertionsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final AssertionsUtil assertionsUtil;

    private final BusinessFunctionPermissionService businessFunctionPermissionService;

    public Permission save(Permission permission){

        Optional<Permission> findByName = permissionRepository.findByName(permission.getName());

        return findByName.orElseGet(() -> permissionRepository.save(permission));

    }

    public Page<Permission> listAll(Pageable pageable){
        return permissionRepository.findAll(pageable);
    }

    public Permission findById(int id){
        return findByIdOrThrowNotFound(id);
    }


    public void update(Permission permission){

        findByIdOrThrowNotFound(permission.getId());

        permissionRepository.save(permission);

    }

    public void delete(Integer id){

        Permission permission = findByIdOrThrowNotFound(id);

        assertionsUtil.assertPermissionIsNotAssociatedWithBusinessFunction(id);

        permissionRepository.delete(permission);
    }

    public void cascadeDelete(Integer id){

        Permission permission = findByIdOrThrowNotFound(id);

        businessFunctionPermissionService.deleteBasedOnPermissionId(id);

        permissionRepository.delete(permission);
    }

    public Permission findByIdOrThrowNotFound(Integer id){
        return permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }
    public Permission findByPermissionNameOrThrowNotFound(String permission){
        return permissionRepository.findByName(permission).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }

}
