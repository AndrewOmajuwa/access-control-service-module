package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Permission p SET p.name = ?2 WHERE p.id =?1")
    void update(@Param("id") Integer id, @Param("name") String name);

}
