package com.devoteam.accesscontrolservice.requests.put;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePutRequest {

    @Id
    private Integer id;

    @NotBlank(message = "Please insert a valid profile name")
    @Column(unique=true)
    private String name;
}
