package com.devoteam.accesscontrolservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ProfileBusinessFunctionPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "business_function_permission_id")
    private BusinessFunctionPermission businessFunctionPermission;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
