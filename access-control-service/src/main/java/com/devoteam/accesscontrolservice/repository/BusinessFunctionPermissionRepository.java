package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessFunctionPermissionRepository extends JpaRepository<BusinessFunctionPermission, Integer> {
    @Query("SELECT bfp FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction = ?1 AND bfp.permission = ?2")
    List<BusinessFunctionPermission> findBusinessFunctionPermission(BusinessFunction businessFunction, Permission permission);

    @Query("SELECT bfp FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction.applicationName = ?1")
    Page<BusinessFunctionPermission> findBusinessFunctionPermissionByApplicationName(String applicationName, Pageable pageable);

}
