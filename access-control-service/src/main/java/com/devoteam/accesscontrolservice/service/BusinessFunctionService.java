package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BusinessFunctionService {

    private final BusinessFunctionRepository businessFunctionRepository;

    public BusinessFunction save(BusinessFunction businessFunction){

        List<BusinessFunction> byApplicationNameAndFunctionName = businessFunctionRepository.findByApplicationNameAndFunctionName(businessFunction.getApplicationName(), businessFunction.getFunctionName());

        return !byApplicationNameAndFunctionName.isEmpty() ? byApplicationNameAndFunctionName.get(0) : businessFunctionRepository.save(businessFunction);

    }
    public void findById(Integer id){
        businessFunctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business Function was not found"));
    }


    public List<BusinessFunction> listAll(){
        return businessFunctionRepository.findAll();
    }

}
