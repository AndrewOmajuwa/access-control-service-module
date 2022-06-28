package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessFunctionMapper {
    BusinessFunctionMapper INSTANCE = Mappers.getMapper (BusinessFunctionMapper.class);

    BusinessFunction toBusinessFunction(BusinessFunctionPostRequest businessFunctionPostRequest);

    BusinessFunctionResponse toBusinessFunctionResponse(BusinessFunction businessFunction);
}
