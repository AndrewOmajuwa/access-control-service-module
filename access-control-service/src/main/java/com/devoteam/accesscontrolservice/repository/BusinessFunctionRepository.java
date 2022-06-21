package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessFunctionRepository extends JpaRepository<BusinessFunction, Integer> {

    List<BusinessFunction> findByApplicationNameAndFunctionName(String applicationName, String functionName);
}
