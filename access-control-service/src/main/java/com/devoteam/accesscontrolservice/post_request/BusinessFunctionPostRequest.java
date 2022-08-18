package com.devoteam.accesscontrolservice.post_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BusinessFunctionPostRequest {

    @NotEmpty
    private String applicationName;
    @NotEmpty
    private String functionName;
}