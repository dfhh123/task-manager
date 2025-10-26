package com.home.userservice.application.dto;

import com.home.userservice.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for {@link User}
 */
public record UserCreateRequestDto(

        @Email @NotBlank String email,

        @NotBlank String language,

        @NotBlank String timezone)

{}