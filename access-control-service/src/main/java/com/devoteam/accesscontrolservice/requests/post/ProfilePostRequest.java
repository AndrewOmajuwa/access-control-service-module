package com.devoteam.accesscontrolservice.requests.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProfilePostRequest {

        @NotEmpty
        private String name;

}
