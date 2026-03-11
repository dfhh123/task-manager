package com.home.notificationservice.dto;

import java.util.UUID;

/**
 * @param publicId
 * @param email
 */
public record UserDto(
        String publicId,
        String email
) {
}

