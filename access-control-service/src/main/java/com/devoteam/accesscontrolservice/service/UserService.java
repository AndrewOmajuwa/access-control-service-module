package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<UserKeyCloak> listAll(String email, String firstName,String lastName, Pageable pageable){

        Page<UserKeyCloak> users = userRepository.findUserByEmailLastNameOrFirstName(email, firstName, lastName, pageable);

        return users;
    }


    public UserKeyCloak save(UserKeyCloak userKeyCloak){
        return userRepository.save(userKeyCloak);
    }

    public UserKeyCloak findByIdOrThrowNotFound(String uuid){
        return userRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("User was not found"));
    }
}
