package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
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


    public Permission findByIdOrThrowNotFound(Integer id){
        return permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }
    public Permission findByPermissionNameOrThrowNotFound(String permission){
        return permissionRepository.findByName(permission).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Permission was not found"));
    }

    public void update(Integer id, String name){

        findByIdOrThrowNotFound(id);

        assertNameIsNotNull(name);

        permissionRepository.update(id, name);

    }

    private static void assertNameIsNotNull(String name) {
        if(name == null){
            new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
