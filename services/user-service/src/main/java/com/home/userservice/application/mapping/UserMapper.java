package com.home.userservice.application.mapping;

import com.home.userservice.application.dto.UserCreateRequestDto;
import com.home.userservice.application.dto.UserDto;
import com.home.userservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toEntity(UserDto userDto);

    User updateWithNull(UserDto userDto, @MappingTarget User user);

    User toEntity(UserCreateRequestDto userCreateRequestDto);

    UserCreateRequestDto toUserCreateRequestDto(User user);
}