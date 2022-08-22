package com.devoteam.accesscontrolservice.requests.post;

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
    private Integer businessFunctionId;

    @NotNull
    private Integer permissionId;

}
