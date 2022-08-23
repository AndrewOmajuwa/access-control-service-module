package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionPermissionRepository;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AssertionsUtil {

    private final BusinessFunctionRepository businessFunctionRepository;

    private final BusinessFunctionPermissionRepository businessFunctionPermissionRepository;

    public void assertBusinessFunctionExists(Integer id){

        businessFunctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function does not exist"));

    }
    public void assertBusinessFunctionPermissionExists(Integer id){

        businessFunctionPermissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The business function permission does not exist"));

    }

    public void assertPermissionIsNotAssociatedWithBusinessFunction(Integer id) {

        if(!businessFunctionPermissionRepository.findBusinessFunctionPermissionByPermissionId(id).isEmpty()){

            throw new BadRequest("Cannot delete Permission associated with Business Function");

        }
    }
}
