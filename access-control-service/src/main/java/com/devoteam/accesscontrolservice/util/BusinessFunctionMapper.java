package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.post_request.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.put_request.BusinessFunctionPutRequest;
import com.devoteam.accesscontrolservice.response.BusinessFunctionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessFunctionMapper {
    BusinessFunctionMapper INSTANCE = Mappers.getMapper (BusinessFunctionMapper.class);

    BusinessFunction toBusinessFunction(BusinessFunctionPostRequest businessFunctionPostRequest);

    BusinessFunction toBusinessFunction(BusinessFunctionPutRequest businessFunctionPutRequest);

    BusinessFunctionResponse toBusinessFunctionResponse(BusinessFunction businessFunction);
}
