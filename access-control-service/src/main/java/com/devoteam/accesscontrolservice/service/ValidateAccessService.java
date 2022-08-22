package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.requests.post.ValidateAccessPostRequest;
import com.devoteam.accesscontrolservice.repository.ProfileBusinessFunctionPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ValidateAccessService {
    private final ProfileBusinessFunctionPermissionRepository profileBusinessFunctionPermissionRepository;

    private final BusinessFunctionService businessFunctionService;

    private final PermissionService permissionService;

    public ResponseEntity<Void> validateAccessService(ValidateAccessPostRequest validateAccessPostRequest){

        String uuid = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        businessFunctionService.findByApplicationAndFunctionNamesOrThrowNotFound(validateAccessPostRequest.getApplicationName(), validateAccessPostRequest.getFunctionName());

        permissionService.findByPermissionNameOrThrowNotFound(validateAccessPostRequest.getPermission());

        boolean isAccessAllowed = profileBusinessFunctionPermissionRepository.isAccessAllowed(uuid, validateAccessPostRequest.getApplicationName(), validateAccessPostRequest.getFunctionName(), validateAccessPostRequest.getPermission());

        return isAccessAllowed ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
