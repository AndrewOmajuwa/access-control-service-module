package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessFunctionPermissionRepository extends JpaRepository<BusinessFunctionPermission, Integer> {
    List<BusinessFunctionPermission> findByBusinessFunctionAndPermission(BusinessFunction businessFunction, Permission permission);
}
