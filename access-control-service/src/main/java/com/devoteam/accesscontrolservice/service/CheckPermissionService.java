package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.ValidateAccessPostRequest;
import com.devoteam.accesscontrolservice.exception.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CheckPermissionService {

    private final ValidateAccessService validateAccessService;

    public boolean validateAccess(String applicationName, String functionName, String permission){

        ValidateAccessPostRequest validateAccessPostRequest = ValidateAccessPostRequest.builder()
                .applicationName(applicationName)
                .functionName(functionName)
                .permission(permission)
                .build();

        HttpStatus httpStatus = validateAccessService.validateAccessService(validateAccessPostRequest);
        return httpStatus == HttpStatus.OK;
    }

}
