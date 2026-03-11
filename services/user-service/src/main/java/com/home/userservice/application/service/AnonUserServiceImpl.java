package com.home.userservice.application.service;

import com.home.userservice.application.dto.UserRequestRegistrationDto;
import com.home.userservice.application.dto.UserResponseRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AnonUserServiceImpl implements AnonUserService {

    private final DomainUserService domainUserService;

    private final KeycloakService keycloakService;

    @Override
    public UserResponseRegistrationDto registerUser(UserRequestRegistrationDto dto) {
        String id = keycloakService.registerUser(dto.registrationData());

        domainUserService.createOne(id, dto.domainData());

        return UserResponseRegistrationDto.builder()
                .publicId(id)
                .authenticationData(dto.registrationData())
                .domainData(dto.domainData())
                .build();
    }

}
