package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.ValidateAccessPostRequest;
import com.devoteam.accesscontrolservice.service.ValidateAccessService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/validate-access")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ValidateAccessController {

    private final ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<Void> validateAccess(@Valid @RequestParam String applicationName, String functionName, String permission){

        ValidateAccessPostRequest validateAccessPostRequest = ValidateAccessPostRequest.builder().applicationName(applicationName).functionName(functionName).permission(permission).build();

        return validateAccessService.validateAccessService(validateAccessPostRequest);

    }
}
