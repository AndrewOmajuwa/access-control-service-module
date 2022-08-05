package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.SetUpPostRequest;
import com.devoteam.accesscontrolservice.service.SetUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/setup-users")
@RequiredArgsConstructor
public class SetUpController {

    private final SetUpService setUpService;

    @PostMapping
    public HttpStatus setUp(@Valid @RequestBody SetUpPostRequest setUpPostRequest){

        return setUpService.save(setUpPostRequest);

    }
}
