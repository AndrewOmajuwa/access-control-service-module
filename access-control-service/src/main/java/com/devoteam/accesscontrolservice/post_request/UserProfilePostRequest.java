package com.devoteam.accesscontrolservice.post_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserProfilePostRequest {

    @NotNull
    private String userKeyCloakId;

    @NotNull
    private Integer profileId;

}
