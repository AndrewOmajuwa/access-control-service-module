package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Utility {

    private final BusinessFunctionRepository businessFunctionRepository;

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    public void assertBusinessFunctionExists(Integer id){

        businessFunctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function does not exist"));

    }
    public void assertBusinessFunctionPermissionExists(Integer id){

        businessFunctionPermissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function permission does not exist"));

    }
}
