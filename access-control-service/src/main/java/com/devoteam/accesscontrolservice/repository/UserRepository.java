package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
