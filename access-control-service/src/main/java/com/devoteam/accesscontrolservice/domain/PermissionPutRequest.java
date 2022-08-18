package com.devoteam.accesscontrolservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionPutRequest {
    @Id
    private Integer id;

    @NotBlank(message = "Please insert a valid permission name")
    @Column(unique=true)
    private String name;
}
