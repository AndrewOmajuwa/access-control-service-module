package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.Profile;
import com.devoteam.accesscontrolservice.domain.ProfilePostRequest;
import com.devoteam.accesscontrolservice.domain.ProfilePutRequest;
import com.devoteam.accesscontrolservice.domain.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper (ProfileMapper.class);

    Profile toProfile(ProfilePostRequest profilePostRequest);

    Profile toProfile(ProfilePutRequest profilePutRequest);

    ProfileResponse toProfileResponse(Profile profile);

}
