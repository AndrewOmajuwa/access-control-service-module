package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserKeyCloak, Integer> {

    Optional<UserKeyCloak> searchByEmail(String email);

    Optional<UserKeyCloak> findByUuid(String uuid);

    @Query("SELECT u FROM UserKeyCloak u WHERE u.email = ?1 OR u.firstName = ?2 OR u.lastName = ?3" )
    Page<UserKeyCloak> findUserByEmailLastNameOrFirstName(String email, String firstName,String lastName, Pageable pageable);
}
