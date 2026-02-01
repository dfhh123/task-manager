package com.home.userservice.application.service;

import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserResponseDto;

public interface AuthUserService {

    UserResponseDto getAuthenticatedUser(String externalId);

    UserResponseDto patchAuthenticatedUser(String externalId, UserDomainDataDto dto);

}