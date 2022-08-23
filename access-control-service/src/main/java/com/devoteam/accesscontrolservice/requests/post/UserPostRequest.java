package com.devoteam.accesscontrolservice.requests.post;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class UserPostRequest {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @NotEmpty
    private String password;
}
