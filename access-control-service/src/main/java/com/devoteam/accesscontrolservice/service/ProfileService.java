package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Permission;
import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile save(Profile profile){

        List<Profile> findByName = profileRepository.findByName(profile.getName());

        return !findByName.isEmpty() ? findByName.get(0) : profileRepository.save(profile);

    }


    public Page<Profile> listAll(Pageable pageable){
        return profileRepository.findAll(pageable);
    }

    public Profile findById(int id){
        return findByIdOrThrowNotFound(id);
    }

    public void update(Profile profile){

        findByIdOrThrowNotFound(profile.getId());

        profileRepository.save(profile);

    }

    public Profile findByIdOrThrowNotFound(Integer id){
        return profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile was not found"));
    }
}
