package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionPermission;
import com.devoteam.accesscontrolservice.response.BusinessFunctionPermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessFunctionPermissionMapper {

    BusinessFunctionPermissionMapper INSTANCE = Mappers.getMapper (BusinessFunctionPermissionMapper.class);

    BusinessFunctionPermissionResponse toBusinessFunctionPermissionResponse(BusinessFunctionPermission businessFunctionPermission);

}
