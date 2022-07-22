package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProfileBusinessFunctionPermissionRepository extends JpaRepository<ProfileBusinessFunctionPermission, Integer> {
    @Query("SELECT pbfp FROM ProfileBusinessFunctionPermission pbfp WHERE pbfp.businessFunctionPermission = ?1 AND pbfp.profile = ?2")
    List<ProfileBusinessFunctionPermission> findProfileBusinessFunctionPermission(BusinessFunctionPermission businessFunctionPermission, Profile profile);

    @Query(value = "SELECT COUNT(u.uuid) > 0 FROM UserKeyCloak u join UserProfile up On u.uuid=up.userKeyCloak join ProfileBusinessFunctionPermission pbfp on up.profile = pbfp.profile join BusinessFunctionPermission bfp on bfp.id = pbfp.businessFunctionPermission.id join BusinessFunction bf on bf.id = bfp.businessFunction.id join Permission p on bfp.permission.id = p.id where u.uuid=?1 and bf.applicationName=?2 and bf.functionName=?3 and p.name=?4")
    boolean isAccessAllowed(UUID uuid, String applicationName, String functionName, String permission);

}
