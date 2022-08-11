package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.SetUpPostRequest;
import com.devoteam.accesscontrolservice.service.SetUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/setup-users")
@RequiredArgsConstructor
public class SetUpController {

    private final SetUpService setUpService;

    @PostMapping
    @Transactional
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'set-up', 'create')")
    public ResponseEntity<Void> setUp(@Valid @RequestBody SetUpPostRequest setUpPostRequest){

        setUpService.save(setUpPostRequest);

        return ResponseEntity.ok().build();
    }
}
