package com.home.userservice.application.service;

import com.home.userservice.application.dto.UserRegistrationDataDto;

public interface KeycloakService {
    String registerUser(UserRegistrationDataDto registrationData);
}
