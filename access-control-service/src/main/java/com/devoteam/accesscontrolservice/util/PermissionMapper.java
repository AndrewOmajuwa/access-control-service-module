package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {

    PermissionMapper INSTANCE = Mappers.getMapper (PermissionMapper.class);

    Permission toPermission(PermissionPostRequest permissionPostRequest);

    PermissionResponse toPermissionResponse(Permission permission);

}
