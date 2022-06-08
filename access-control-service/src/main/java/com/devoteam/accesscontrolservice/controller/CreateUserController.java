package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.User;
import com.devoteam.accesscontrolservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("createuser")
@RequiredArgsConstructor
public class CreateUserController {

    private UserService userService;
    private final KeycloakAdminClient keycloakAdminClient;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid String firstName, String lastName, String email){
        User user = keycloakAdminClient.createUser(firstName, lastName, email);
        return ResponseEntity.ok(userService.save(user));
    }

}


