package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserKeycloakMapper {

    UserKeycloakMapper INSTANCE = Mappers.getMapper (UserKeycloakMapper.class);

    UserResponse toUserResponse(String uuid);
}