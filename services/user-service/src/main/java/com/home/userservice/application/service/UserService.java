package com.home.userservice.application.service;

import com.home.userservice.adapters.in.web.rest.UserFilter;
import com.home.userservice.application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto delete(Long id);

    UserDto create(UserDto dto);

    UserDto update(Long id, UserDto dto);

    UserDto getOne(Long id);

    Page<UserDto> getAll(UserFilter filter, Pageable pageable);
}
