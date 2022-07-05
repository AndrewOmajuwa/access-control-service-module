package com.devoteam.accesscontrolservice.service;

import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.exception.ResourceNotFoundException;
import com.devoteam.accesscontrolservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
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

    public Profile findByIdOrThrowNotFound(Integer id){
        return profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile was not found"));
    }
}
