package com.home.userservice.application.service;

import com.home.userservice.adapters.in.web.rest.UserFilter;
import com.home.userservice.application.dto.UserDto;
import com.home.userservice.application.mapping.UserMapper;
import com.home.userservice.adapters.out.persistence.jpa.UserRepository;
import com.home.userservice.domain.entity.User;
import com.home.userservice.errors.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("User with id `%s` not found".formatted(id)));
        if (user != null) {
            userRepository.delete(user);
        }
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto create(UserDto dto) {
        User user = userMapper.toEntity(dto);
        User resultUser = userRepository.save(user);
        return userMapper.toUserDto(resultUser);
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        userMapper.updateWithNull(dto, user);
        User resultUser = userRepository.save(user);
        return userMapper.toUserDto(resultUser);
    }

    @Override
    public UserDto getOne(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userMapper.toUserDto(userOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    @Override
    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toUserDto);
    }
}
