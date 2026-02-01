package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserResponseDto;
import com.home.userservice.application.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/api/rest/{version}/users/me", version = "v1")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService userService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getAuthenticatedUser(Authentication auth) {
        String name = auth.getName();
        return userService.getAuthenticatedUser(name);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto patchAuthenticatedUser(Authentication auth,
                                                  @RequestBody UserDomainDataDto dto) {
        String name = auth.getName();
        return userService.patchAuthenticatedUser(name, dto);
    }
}
