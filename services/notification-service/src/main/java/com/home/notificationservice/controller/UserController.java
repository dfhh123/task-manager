package com.home.notificationservice.controller;

import com.home.notificationservice.service.UserService;
import com.home.notificationservice.dto.UserDto;
import com.home.notificationservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/{version}/users", version = "v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{publicId}")
    public User getUser(@PathVariable String publicId) {
        return userService.getUser(publicId);
    }

    @DeleteMapping("/{publicId}")
    public void deleteUser(@PathVariable String publicId) {
        userService.deleteUser(publicId);
    }

    @PostMapping
    public User createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }
}
