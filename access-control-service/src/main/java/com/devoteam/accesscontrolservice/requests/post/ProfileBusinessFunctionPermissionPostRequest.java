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
public class ProfileBusinessFunctionPermissionPostRequest {

    @NotNull
    private Integer businessFunctionPermissionId;

    @NotNull
    private Integer profileId;

}
