package com.home.userservice.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.home.userservice.domain.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AdminResponseDto(UUID id, String keycloakId, String email, String language, String timezone,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {
}