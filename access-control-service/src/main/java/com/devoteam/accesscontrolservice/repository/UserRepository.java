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

    Optional<UserKeyCloak> findByUuid(UUID uuid);


}
