package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    List<Permission> findByName(String name);
    @Query(value = "select p from Permission p where p.name = ?1")
    Optional<Permission> findByPermissionName(String name);
}
