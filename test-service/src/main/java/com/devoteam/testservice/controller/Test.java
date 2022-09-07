package com.devoteam.microservicetest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
@SecurityRequirement(name = "bearerAuth")
public class Test {

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'business-function', 'view')")
    public List<String> getNames(){
        return List.of("Andrew", "Micheal", "Wayne");
    }
}
