package com.home.userservice.application.dto;

import lombok.Builder;

@Builder
public record UserRequestRegistrationDto (
        UserRegistrationDataDto registrationData,
        UserDomainDataDto domainData
) {
}
