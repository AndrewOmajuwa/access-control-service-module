package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.User;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
import com.devoteam.accesscontrolservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }


    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody UserPostRequest userPostRequest){
        return ResponseEntity.ok(userService.save(userPostRequest));
    }
}


