package com.home.userservice.application.service;

import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserRequestRegistrationDto;
import com.home.userservice.application.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserService {

    void deleteOne(String id);

    UserResponseDto createOne(@Valid UserRequestRegistrationDto registrationDataDto);

    UserResponseDto putOne(String id, @Valid UserDomainDataDto dto);

    UserResponseDto patchOne(String id, @Valid UserDomainDataDto dto);

    UserResponseDto getOne(String id);

    Page<UserResponseDto> getAll(Pageable pageable);
}
