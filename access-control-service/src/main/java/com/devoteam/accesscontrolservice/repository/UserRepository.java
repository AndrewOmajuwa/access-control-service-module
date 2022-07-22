package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserKeyCloak, Integer> {

    List<UserKeyCloak> searchByEmail(String email);

//    @Query(value = "SELECT u FROM UserKeyCloak u WHERE u.uuid = ?1")
    Optional<UserKeyCloak> findByUuid(UUID uuid);


}
