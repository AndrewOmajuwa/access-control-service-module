package com.devoteam.library.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ValidateAccessPostRequest {
    @NotEmpty
    private String applicationName;
    @NotEmpty
    private String functionName;
    @NotEmpty
    private String permission;
}
