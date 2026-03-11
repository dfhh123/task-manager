package com.home.notificationservice.service;

import com.home.notificationservice.dto.UserDto;
import com.home.notificationservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.UserEmailInfo;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(String publicId) {
        return userRepository.findById(publicId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(String publicId) {
        userRepository.deleteById(publicId);
    }

    @Override
    public User createUser(UserDto userDto) {
        return userRepository.save(new User(userDto.publicId(), userDto.email(), new ArrayList<>(0)));
    }

    @Override
    public String getUserEmail(String publicId) {
        return userRepository.findByPublicId(publicId).getEmail();
    }
}
