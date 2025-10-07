package com.home.userservice.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link com.home.userservice.domain.entity.User}
 */
public record UserDto(

        @NotNull
        @Min(value = 1, message = "Id must be greater than 0")
        Long id,

        @NotNull
        @Email
        String email) {
}