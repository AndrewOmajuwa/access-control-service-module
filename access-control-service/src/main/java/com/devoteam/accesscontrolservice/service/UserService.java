package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserKeyCloak> findAll(){
        return userRepository.findAll();
    }

    public UserKeyCloak save(UserKeyCloak userKeyCloak){
        return userRepository.save(userKeyCloak);
    }
}
