package com.home.userservice.application.service;

import com.home.userservice.adapters.out.persistence.jpa.UserRepository;
import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserResponseDto;
import com.home.userservice.application.mapping.UserMapper;
import com.home.userservice.application.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final UserRepository authUserRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto getAuthenticatedUser(String externalId) {
        return userMapper.toUserResponseDto(authUserRepository.findUserByKeycloakId(externalId).orElseThrow(() ->
                new UserNotFoundException("User not found")));
    }

    @Override
    public UserResponseDto patchAuthenticatedUser(String externalId, UserDomainDataDto dto) {
        var user = authUserRepository.findUserByKeycloakId(externalId).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        if(dto.language() != null) user.setLanguage(dto.language());
        if (dto.timezone() != null) user.setTimezone(dto.timezone());

        return userMapper.toUserResponseDto(authUserRepository.save(user));
    }
}
