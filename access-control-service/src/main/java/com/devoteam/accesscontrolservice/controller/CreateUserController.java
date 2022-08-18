package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.post_request.UserPostRequest;
import com.devoteam.accesscontrolservice.response.UserResponse;
import com.devoteam.accesscontrolservice.service.UserService;
import com.devoteam.accesscontrolservice.util.UserKeycloakMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CreateUserController {

    private final UserService userService;

    private final KeycloakAdminClient keycloakAdminClient;

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'userKeyCloak', 'view')")
    public ResponseEntity<Page<UserKeyCloak>> getUsers( @RequestParam(value = "email", required = false) String email,
                                                        @RequestParam(value = "firstName", required = false) String firstName,
                                                        @RequestParam(value = "lastName", required = false) String lastName,
                                                        Pageable pageable){
        return ResponseEntity.ok(userService.listAll(email, firstName, lastName, pageable));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserPostRequest userPostRequest){

        String userUuid = keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword());

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

    public void assertUuidIsNotNull(String uuid){
        if(uuid == null){
            throw new BadRequest("User was not created in Keycloak");
        }
    }
}


