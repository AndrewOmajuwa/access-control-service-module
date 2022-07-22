package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.UserKeyCloak;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
import com.devoteam.accesscontrolservice.domain.UserResponse;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.service.UserService;
import com.devoteam.accesscontrolservice.util.UserKeycloakMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/users")
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;

    private final KeycloakAdminClient keycloakAdminClient;

    @GetMapping
    public ResponseEntity<List<UserKeyCloak>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }


    @PostMapping
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserPostRequest userPostRequest){

        UUID userUuid = keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword());

        assertUuidIsNotNull(userUuid);
        UserKeyCloak userKeyCloak = UserKeyCloak.builder()
                .uuid(userUuid)
                .firstName(userPostRequest.getFirstName())
                .lastName(userPostRequest.getLastName())
                .email(userPostRequest.getEmail())
                .build();

        userService.save(userKeyCloak);
        UserResponse userResponse = UserKeycloakMapper.INSTANCE.toUserResponse(userUuid);

        return ResponseEntity.ok(userResponse);
    }

    public void assertUuidIsNotNull(UUID uuid){
        if(uuid == null){
            throw new BadRequest("User was not created in Keycloak");
        }
    }
}


