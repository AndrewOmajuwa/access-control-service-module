package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BusinessFunctionService {

    private final BusinessFunctionRepository businessFunctionRepository;

    public BusinessFunction save(BusinessFunction businessFunction){

        List<BusinessFunction> byApplicationNameAndFunctionName = businessFunctionRepository.findByApplicationNameAndFunctionName(businessFunction.getApplicationName(), businessFunction.getFunctionName());

        return !byApplicationNameAndFunctionName.isEmpty() ? byApplicationNameAndFunctionName.get(0) : businessFunctionRepository.save(businessFunction);

    }

    public List<BusinessFunction> listAll(){
        return businessFunctionRepository.findAll();
    }

    public BusinessFunction findByIdOrThrowNotFound(Integer id){
        return businessFunctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business Function was not found"));
    }
    public BusinessFunction findByNameOrThrowNotFound(String applicationName, String functionName){
        return businessFunctionRepository.findByApplicationAndFunctionName(applicationName, functionName).orElseThrow(() -> new ResourceNotFoundException("Business Function was not found"));
    }

}
