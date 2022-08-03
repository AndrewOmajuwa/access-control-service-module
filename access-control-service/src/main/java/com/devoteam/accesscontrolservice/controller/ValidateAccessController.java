package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.ValidateAccessPostRequest;
import com.devoteam.accesscontrolservice.service.ValidateAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/validate-access")
@RequiredArgsConstructor
public class ValidateAccessController {

    private final ValidateAccessService validateAccessService;

    @PostMapping
    public HttpStatus validateAccess(@Valid @RequestBody ValidateAccessPostRequest validateAccessPostRequest){

        return validateAccessService.validateAccessService(validateAccessPostRequest);

    }
}
