package com.home.userservice.application.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponseDto(
        String publicId,
        String language,
        String timezone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}