package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.User;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakAdminClient keycloakAdminClient;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(UserPostRequest userPostRequest){

        UUID userUuid = keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail());

        assertUuidIsNotNull(userUuid);

        User user = User.builder()
                .uuid(userUuid)
                .firstName(userPostRequest.getFirstName())
                .lastName(userPostRequest.getLastName())
                .email(userPostRequest.getEmail())
                .build();

        return userRepository.save(user);
    }

    public void assertUuidIsNotNull(UUID uuid){
        if(uuid == null){
            throw new BadRequest("User was not created in Keycloak");
        }
    }
}
