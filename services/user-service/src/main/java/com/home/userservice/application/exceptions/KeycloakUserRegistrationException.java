package com.home.userservice.application.exceptions;

import com.home.userservice.application.dto.UserRegistrationDataDto;

public class KeycloakUserRegistrationException extends RuntimeException {

    private final UserRegistrationDataDto registrationData;

    public KeycloakUserRegistrationException(String message, UserRegistrationDataDto registrationData) {
        super(message);
        this.registrationData = registrationData;
    }


    public UserRegistrationDataDto getUser() {
        return registrationData;
    }
}
