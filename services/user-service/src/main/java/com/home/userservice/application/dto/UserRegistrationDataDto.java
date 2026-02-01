package com.home.userservice.application.dto;

import lombok.Builder;

@Builder
public record UserRegistrationDataDto (
        String email,
        String password
) {
}
