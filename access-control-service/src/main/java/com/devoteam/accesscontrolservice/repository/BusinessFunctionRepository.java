package com.devoteam.accesscontrolservice.repository;

import com.devoteam.accesscontrolservice.domain.BusinessFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BusinessFunctionRepository extends JpaRepository<BusinessFunction, Integer> {

    Optional<BusinessFunction> findByApplicationNameAndFunctionName(String applicationName, String functionName);

    @Transactional
    @Modifying
    @Query("UPDATE BusinessFunction bf SET bf.applicationName = ?2, bf.functionName = ?3 WHERE bf.id =?1")
    void update(@Param("id") Integer id, @Param("applicationName") String applicationName, @Param("functionName") String functionName);
}
