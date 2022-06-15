package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunctionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessFunctionRepository extends JpaRepository<BusinessFunctionResponse, Integer> {

    List<BusinessFunctionResponse> findByApplicationNameAndFunctionName(String applicationName, String functionName);
}
