package com.devoteam.accesscontrolservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("names")
@SecurityRequirement(name = "bearerAuth")
public class NamesController {

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'admin', 'create')")
    public List<String> names(){
        return Arrays.asList(
                "Stan",
                "Kyle",
                "Kenny",
                "Cartman"
        );
    }
}
