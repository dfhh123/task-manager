package com.home.userservice.application.dto;

import lombok.Builder;

@Builder
public record UserResponseRegistrationDto(
        String publicId,
        UserRegistrationDataDto authenticationData,
        UserDomainDataDto domainData
) {
}
