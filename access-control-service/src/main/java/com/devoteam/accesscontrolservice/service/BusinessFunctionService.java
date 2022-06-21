package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BusinessFunctionService {
    final private BusinessFunctionRepository businessFunctionRepository;

    public BusinessFunction save(BusinessFunction businessFunction){

        List<BusinessFunction> byApplicationNameAndFunctionName = businessFunctionRepository.findByApplicationNameAndFunctionName(businessFunction.getApplicationName(), businessFunction.getFunctionName());

        return byApplicationNameAndFunctionName.size() > 0 ? byApplicationNameAndFunctionName.get(0) : businessFunctionRepository.save(businessFunction);

    }

    public List<BusinessFunction> listAll(){
        return businessFunctionRepository.findAll();
    }

}
