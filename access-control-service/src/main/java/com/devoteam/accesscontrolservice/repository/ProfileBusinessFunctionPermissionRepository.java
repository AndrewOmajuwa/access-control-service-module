package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileBusinessFunctionPermissionRepository extends JpaRepository<ProfileBusinessFunctionPermission, Integer> {
    @Query("SELECT pbfp FROM ProfileBusinessFunctionPermission pbfp WHERE pbfp.businessFunctionPermission = ?1 AND pbfp.profile = ?2")
    List<ProfileBusinessFunctionPermission> findProfileBusinessFunctionPermission(BusinessFunctionPermission businessFunctionPermission, Profile profile);
}
