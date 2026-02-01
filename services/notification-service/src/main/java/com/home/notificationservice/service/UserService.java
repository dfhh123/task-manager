package com.home.notificationservice.service;

import com.home.notificationservice.dto.UserDto;
import com.home.notificationservice.entity.User;
import repository.UserEmailInfo;

import java.util.UUID;

public interface UserService {

    User getUser(String publicId);

    void deleteUser(String publicId);

    User createUser(UserDto userDto);

    String getUserEmail(UUID publicId);
}
