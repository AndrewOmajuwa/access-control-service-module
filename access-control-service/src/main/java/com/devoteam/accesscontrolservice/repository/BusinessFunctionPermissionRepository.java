package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BusinessFunctionPermissionRepository extends JpaRepository<BusinessFunctionPermission, Integer> {
    @Query("SELECT bfp FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction = ?1 AND bfp.permission = ?2")
    List<BusinessFunctionPermission> findBusinessFunctionPermission(BusinessFunction businessFunction, Permission permission);

    @Query("SELECT bfp FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction.applicationName = ?1")
    Page<BusinessFunctionPermission> findBusinessFunctionPermissionByApplicationName(String applicationName, Pageable pageable);

    @Query("SELECT bfp FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction.id = ?1")
    List<BusinessFunctionPermission> listBusinessFunctionPermissionByBusinessFunctionId(Integer businessFunctionId);

    @Modifying
    @Query("DELETE FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction.id IN ?1")
    void deleteBusinessFunctionPermissionByBusinessFunctionId(Integer businessFunctionId);

}
