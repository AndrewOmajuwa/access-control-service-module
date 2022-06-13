package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> searchByEmail(String email);
}
