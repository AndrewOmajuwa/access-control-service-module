package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    List<Profile> findByName(String name);
}
