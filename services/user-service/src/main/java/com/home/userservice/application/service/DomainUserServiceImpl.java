package com.home.userservice.application.service;

import com.home.avro.event.user.UserCreatedEvent;
import com.home.avro.event.user.UserDeletedEvent;
import com.home.avro.event.user.UserUpdatedEvent;
import com.home.userservice.application.dto.*;
import com.home.userservice.application.mapping.UserMapper;
import com.home.userservice.adapters.out.persistence.jpa.UserRepository;
import com.home.userservice.domain.entity.User;
import com.home.userservice.application.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class DomainUserServiceImpl implements DomainUserService {

    private final ApplicationEventPublisher eventPublisher;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDto getOne(String id) {
        Optional<User> userOptional = userRepository.findUserByKeycloakId(id);
        return userMapper.toUserResponseDto(userOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto createOne(String publicId, UserDomainDataDto dto) {
        User user = userMapper.toEntity(dto);
        user.setKeycloakId(publicId);
        User resultUser = userRepository.save(user);

        eventPublisher.publishEvent(new UserCreatedEvent(resultUser.getKeycloakId()));
        return userMapper.toUserResponseDto(resultUser);
    }

    @Override
    public UserResponseDto deleteOne(String id) {
        User user = userRepository.findUserByKeycloakId(id).orElseThrow(()
                -> new UserNotFoundException("User with id `%s` not found".formatted(id)));

        userRepository.deleteUserByKeycloakId(id);

        eventPublisher.publishEvent(new UserDeletedEvent(id));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto putOne(String id, UserDomainDataDto dto) {
        User user = userRepository.findUserByKeycloakId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        userMapper.updateWithNull(dto, user);
        User resultUser = userRepository.save(user);

        eventPublisher.publishEvent(new UserUpdatedEvent(resultUser.getKeycloakId()));
        return userMapper.toUserResponseDto(resultUser);
    }

    @Override
    public UserResponseDto patchOne(String id, UserDomainDataDto dto) {
        var user = userRepository.findUserByKeycloakId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User %s not found".formatted(id)));

        userMapper.updateWithNull(dto, user);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }
}
