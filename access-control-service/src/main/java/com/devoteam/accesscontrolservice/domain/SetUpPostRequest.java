package com.devoteam.accesscontrolservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetUpPostRequest {

    @NotEmpty
    private String applicationName;

    @NotEmpty
    private String functionName;

    @NotEmpty
    private String permission;

    @NotEmpty
    private String profileName;

    @NotEmpty
    private String email;
}
