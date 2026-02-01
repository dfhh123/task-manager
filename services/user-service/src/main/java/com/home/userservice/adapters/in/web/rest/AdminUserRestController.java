package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.*;
import com.home.userservice.application.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/rest/{version}/users", version = "v1")
@RequiredArgsConstructor
@Validated
public class AdminUserRestController {

    private final AdminUserService adminUserService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
         adminUserService.deleteOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody @Valid UserRequestRegistrationDto dto) {
        return adminUserService.createOne(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto putUser(@PathVariable String id, @RequestBody @Valid UserDomainDataDto dto) {
        return adminUserService.putOne(id, dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto patchUser(@PathVariable String id, @RequestBody @Valid UserDomainDataDto dto) {
        return adminUserService.patchOne(id, dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getOneUser(@PathVariable String id) {
        return adminUserService.getOne(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserResponseDto> getAllUsers(Pageable pageable) {
        Page<UserResponseDto> userDtos = adminUserService.getAll(pageable);
        return new PagedModel<>(userDtos);
    }
}

