package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.UserRequestRegistrationDto;
import com.home.userservice.application.dto.UserResponseRegistrationDto;
import com.home.userservice.application.service.AnonUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rest/{version}/users/", version = "1")
@AllArgsConstructor
public class AnonUserController {

    private final AnonUserService service;

    @PostMapping(value = "register")
    public UserResponseRegistrationDto registerUser(@RequestBody UserRequestRegistrationDto dto) {
        return service.registerUser(dto);
    }

}
