package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    @Query("SELECT up FROM UserProfile up WHERE up.userKeyCloak = ?1 AND up.profile = ?2")
    List<UserProfile> findUserProfile(UserKeyCloak userKeyCloak, Profile profile);
}
