package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.ValidateAccessPostRequest;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.service.BusinessFunctionService;
import com.devoteam.accesscontrolservice.service.PermissionService;
import com.devoteam.accesscontrolservice.service.ValidateAccessService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/validate-access")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ValidateAccessController {

    private final ValidateAccessService validateAccessService;

    @PostMapping
    public HttpStatus validateAccess(@Valid @RequestBody ValidateAccessPostRequest validateAccessPostRequest){

        return validateAccessService.validateAccessService(validateAccessPostRequest);

    }
}
