package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserKeyCloak, Integer> {

    List<UserKeyCloak> searchByEmail(String email);
}
