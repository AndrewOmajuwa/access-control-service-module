package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.domain.ProfileBusinessFunctionPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfileBusinessFunctionPermissionRepository extends JpaRepository<ProfileBusinessFunctionPermission, Integer> {
    @Query("SELECT pbfp FROM ProfileBusinessFunctionPermission pbfp WHERE pbfp.businessFunctionPermission = ?1 AND pbfp.profile = ?2")
    List<ProfileBusinessFunctionPermission> findProfileBusinessFunctionPermission(BusinessFunctionPermission businessFunctionPermission, Profile profile);

    @Query(value = "SELECT COUNT(u.uuid) > 0 FROM UserKeyCloak u join UserProfile up On u.uuid=up.userKeyCloak join ProfileBusinessFunctionPermission pbfp on up.profile = pbfp.profile join BusinessFunctionPermission bfp on bfp.id = pbfp.businessFunctionPermission.id join BusinessFunction bf on bf.id = bfp.businessFunction.id join Permission p on bfp.permission.id = p.id where u.uuid=?1 and bf.applicationName=?2 and bf.functionName=?3 and p.name=?4")
    boolean isAccessAllowed(String uuid, String applicationName, String functionName, String permission);

    @Query("SELECT pbfp FROM ProfileBusinessFunctionPermission pbfp WHERE pbfp.profile.name = ?1")
    Page<ProfileBusinessFunctionPermission> findBusinessFunctionPermissionByProfileName(String profileName, Pageable pageable);

    @Modifying
    @Query("DELETE FROM ProfileBusinessFunctionPermission pbfp WHERE pbfp.businessFunctionPermission.id IN ?1")
    void deleteProfileBusinessFunctionPermissionByBusinessFunctionPermissionId(List<Integer> businessFunctionPermissionId);

}
