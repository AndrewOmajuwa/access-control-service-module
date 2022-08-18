package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.post_request.PermissionPostRequest;
import com.devoteam.accesscontrolservice.put_request.PermissionPutRequest;
import com.devoteam.accesscontrolservice.response.PermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {

    PermissionMapper INSTANCE = Mappers.getMapper (PermissionMapper.class);

    Permission toPermission(PermissionPostRequest permissionPostRequest);

    Permission toPermission(PermissionPutRequest permissionPutRequest);

    PermissionResponse toPermissionResponse(Permission permission);

}
