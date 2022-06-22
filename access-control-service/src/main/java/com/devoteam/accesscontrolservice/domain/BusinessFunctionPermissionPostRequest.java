package com.devoteam.accesscontrolservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusinessFunctionPermissionPostRequest {

    @NotNull
    private BusinessFunction businessFunction;

    @NotNull
    private Permission permission;

}
