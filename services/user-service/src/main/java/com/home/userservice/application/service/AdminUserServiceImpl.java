package com.home.userservice.application.service;

import com.home.userservice.application.dto.AdminResponseDto;
import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserRegistrationDataDto;
import com.home.userservice.application.dto.UserRequestRegistrationDto;
import com.home.userservice.application.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final DomainUserService userService;

    private final KeycloakService keycloakService;

    @Override
    public void deleteOne(String id) {
        userService.deleteOne(id);
    }

    @Override
    public UserResponseDto createOne(UserRequestRegistrationDto dto) {
        String id = keycloakService.registerUser(dto.registrationData());
        UserResponseDto one = userService.createOne(id, dto.domainData());
        return one;
    }

    @Override
    public UserResponseDto putOne(String id, UserDomainDataDto dto) {
        return userService.putOne(id, dto);
    }

    @Override
    public UserResponseDto patchOne(String id, UserDomainDataDto dto) {
        return userService.patchOne(id, dto);
    }

    @Override
    public UserResponseDto getOne(String id) {
        return userService.getOne(id);
    }

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }
}
