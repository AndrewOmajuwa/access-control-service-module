package com.devoteam.accesscontrolservice.controller;

import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.requests.post.ProfilePostRequest;
import com.devoteam.accesscontrolservice.requests.put.ProfilePutRequest;
import com.devoteam.accesscontrolservice.response.ProfileResponse;
import com.devoteam.accesscontrolservice.service.ProfileService;
import com.devoteam.accesscontrolservice.util.ProfileMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'create')")
    public ResponseEntity<ProfileResponse> save(@Valid @RequestBody ProfilePostRequest profilePostRequest){

        Profile profile = ProfileMapper.INSTANCE.toProfile(profilePostRequest);

        Profile savedProfile = profileService.save(profile);

        ProfileResponse profileResponse = ProfileMapper.INSTANCE.toProfileResponse(savedProfile);

        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'view')")
    public ResponseEntity<Page<Profile>> getProfile(Pageable pageable){
        return ResponseEntity.ok(profileService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'view')")
    public ResponseEntity<Profile> findById(@PathVariable int id) {
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PutMapping
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'update')")
    public ResponseEntity<Void> update(@RequestBody @Valid ProfilePutRequest profilePutRequest) {

        Profile profile = ProfileMapper.INSTANCE.toProfile(profilePutRequest);

        profileService.update(profile);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'delete')")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        profileService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(path = "/{id}/cascade")
    @Transactional
    @PreAuthorize("@checkPermissionService.validateAccess('access-control-service', 'profile', 'cascade-delete')")
    public ResponseEntity<Void> cascadeDelete(@PathVariable int id) {

        profileService.cascadeDelete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
