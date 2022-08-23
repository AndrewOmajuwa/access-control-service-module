package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BusinessFunctionService {

    private final BusinessFunctionRepository businessFunctionRepository;

    private final BusinessFunctionPermissionService businessFunctionPermissionService;

    public BusinessFunction save(BusinessFunction businessFunction){

        Optional<BusinessFunction> byApplicationNameAndFunctionName = businessFunctionRepository.findByApplicationNameAndFunctionName(businessFunction.getApplicationName(), businessFunction.getFunctionName());

        return byApplicationNameAndFunctionName.orElseGet(() -> businessFunctionRepository.save(businessFunction));

    }

    public List<BusinessFunction> listAll(){
        return businessFunctionRepository.findAll();
    }

    public Page<BusinessFunction> listAll(Pageable pageable){
        return businessFunctionRepository.findAll(pageable);
    }

    public BusinessFunction findById(int id){
        return findByIdOrThrowNotFound(id);
    }

    public void update(BusinessFunction businessFunction){

        findByIdOrThrowNotFound(businessFunction.getId());

        businessFunctionRepository.save(businessFunction);

    }

    public void delete(Integer id){

        BusinessFunction businessFunction = findByIdOrThrowNotFound(id);

        assertBusinessFunctionIsNotAssociatedWithPermission(businessFunction);

        businessFunctionRepository.delete(businessFunction);
    }

    public void cascadeDelete(Integer id){

        BusinessFunction businessFunction = findByIdOrThrowNotFound(id);

        businessFunctionPermissionService.deleteBasedOnBusinessFunctionId(id);

        businessFunctionRepository.delete(businessFunction);
    }

    private void assertBusinessFunctionIsNotAssociatedWithPermission(BusinessFunction businessFunction) {

        if(!businessFunctionRepository.findBusinessFunctionPermissionByBusinessFunction(businessFunction).isEmpty()){

            throw new BadRequest("Cannot delete Business Functions associated with Permissions");

        }
    }

    public BusinessFunction findByIdOrThrowNotFound(Integer id){
        return businessFunctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business Function was not found"));
    }
    public BusinessFunction findByApplicationAndFunctionNamesOrThrowNotFound(String applicationName, String functionName){
        return businessFunctionRepository.findByApplicationNameAndFunctionName(applicationName, functionName).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Business Function was not found"));
    }

}
