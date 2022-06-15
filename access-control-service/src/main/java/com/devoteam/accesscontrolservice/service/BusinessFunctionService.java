package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BusinessFunctionService {
    final private BusinessFunctionRepository businessFunctionRepository;

    public BusinessFunctionResponse save(BusinessFunctionResponse businessFunctionResponse){

        List<BusinessFunctionResponse> byApplicationNameAndFunctionName = businessFunctionRepository.findByApplicationNameAndFunctionName(businessFunctionResponse.getApplicationName(), businessFunctionResponse.getFunctionName());

        if(byApplicationNameAndFunctionName.stream().findAny().isPresent()){
            return byApplicationNameAndFunctionName.stream().findFirst().get();
        }

        return businessFunctionRepository.save(businessFunctionResponse);
    }

    public List<BusinessFunctionResponse> listAll(){
        return businessFunctionRepository.findAll();
    }

}
