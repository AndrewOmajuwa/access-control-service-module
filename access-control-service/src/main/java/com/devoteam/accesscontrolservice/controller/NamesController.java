package com.devoteam.accesscontrolservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("names")
public class NamesController {

    @GetMapping
    public List<String> names(){
        return Arrays.asList(
                "Stan",
                "Kyle",
                "Kenny",
                "Cartman"
        );
    }
}
