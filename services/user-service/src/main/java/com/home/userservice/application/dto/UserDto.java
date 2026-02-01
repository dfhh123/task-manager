package com.home.userservice.application.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        UUID publicId,
        UserDomainDataDto domainData,
        LocalDateTime createdAt,
        LocalDateTime updatedAt)
{}