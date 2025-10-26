package com.home.userservice.application.service;

import com.home.userservice.adapters.in.web.rest.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserDto delete(UUID id);

    UserDto create(UserDto dto);

    UserDto update(UUID id, UserDto dto);

    UserDto getOne(UUID id);

    Page<UserDto> getAll(UserFilter filter, Pageable pageable);
}
