package com.devoteam.accesscontrolservice.util;

import com.devoteam.accesscontrolservice.domain.UserProfile;
import com.devoteam.accesscontrolservice.response.UserProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper (UserProfileMapper.class);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

}
