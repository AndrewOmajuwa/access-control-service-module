package com.devoteam.accesscontrolservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BusinessFunctionPutRequest {

    @Id
    private Integer id;
    @NotBlank(message = "Please insert a valid application name")
    private String applicationName;
    @NotBlank(message = "Please insert a valid function name")
    private String functionName;
}
