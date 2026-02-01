package com.home.userservice.application.service;

import com.home.userservice.application.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DomainUserService {

    UserResponseDto getOne(String id);

    Page<UserResponseDto> getAll(Pageable pageable);

    UserResponseDto createOne( String publicId, UserDomainDataDto dto);

    UserResponseDto putOne(String id, UserDomainDataDto dto);

    UserResponseDto patchOne(String id, @Valid UserDomainDataDto dto);

    UserResponseDto deleteOne(String id);

}
