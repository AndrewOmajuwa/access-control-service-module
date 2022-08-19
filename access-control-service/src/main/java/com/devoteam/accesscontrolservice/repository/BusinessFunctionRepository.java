package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.ProfileBusinessFunctionPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessFunctionRepository extends JpaRepository<BusinessFunction, Integer> {

    Optional<BusinessFunction> findByApplicationNameAndFunctionName(String applicationName, String functionName);

    @Query("SELECT bfp FROM BusinessFunctionPermission bfp WHERE bfp.businessFunction = ?1")
    List<BusinessFunctionPermission> findBusinessFunctionPermissionByBusinessFunction(BusinessFunction businessFunction);

}
