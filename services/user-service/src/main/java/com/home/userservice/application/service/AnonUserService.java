package com.home.userservice.application.service;

import com.home.userservice.application.dto.UserRequestRegistrationDto;
import com.home.userservice.application.dto.UserResponseRegistrationDto;

public interface AnonUserService {

    UserResponseRegistrationDto registerUser(UserRequestRegistrationDto dto);

}
