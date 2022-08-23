package com.devoteam.accesscontrolservice.requests.put;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
