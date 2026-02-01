package com.home.userservice.application.mapping;

import com.home.userservice.application.dto.*;
import com.home.userservice.application.dto.AdminResponseDto;
import com.home.userservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toEntity(UserDto userDto);

    User updateWithNull(UserDomainDataDto userDto, @MappingTarget User user);

    User toEntity(UserDomainDataDto userDomainDataDto);

    UserResponseDto toUserResponseDto(User user);

    User toEntity(AdminResponseDto adminResponseDto);

    AdminResponseDto toAdminResposeDto(User user);
}