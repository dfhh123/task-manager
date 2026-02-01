package com.home.userservice.application.dto;

import lombok.Builder;

@Builder
public record UserDomainDataDto(
        String language,
        String timezone
) {
}