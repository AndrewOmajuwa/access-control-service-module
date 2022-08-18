package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.ProfileBusinessFunctionPermission;
import com.devoteam.accesscontrolservice.response.ProfileBusinessFunctionPermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileBusinessFunctionPermissionMapper {

    ProfileBusinessFunctionPermissionMapper INSTANCE = Mappers.getMapper (ProfileBusinessFunctionPermissionMapper.class);

    ProfileBusinessFunctionPermissionResponse toProfileBusinessFunctionPermissionResponse(ProfileBusinessFunctionPermission profileBusinessFunctionPermission);

}
