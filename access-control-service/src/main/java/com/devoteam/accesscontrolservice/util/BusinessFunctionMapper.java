package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.requests.post.BusinessFunctionPostRequest;
import com.devoteam.accesscontrolservice.requests.put.BusinessFunctionPutRequest;
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
