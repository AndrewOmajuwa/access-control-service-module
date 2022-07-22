package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessFunctionRepository extends JpaRepository<BusinessFunction, Integer> {

    List<BusinessFunction> findByApplicationNameAndFunctionName(String applicationName, String functionName);
    @Query(value = "select bf from BusinessFunction bf where bf.applicationName = ?1 and bf.functionName=?2")
    Optional<BusinessFunction> findByApplicationAndFunctionName(String applicationName, String functionName);
}
