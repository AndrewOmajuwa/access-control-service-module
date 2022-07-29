package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface UserKeycloakMapper {

    UserKeycloakMapper INSTANCE = Mappers.getMapper (UserKeycloakMapper.class);

    UserResponse toUserResponse(String uuid);
}