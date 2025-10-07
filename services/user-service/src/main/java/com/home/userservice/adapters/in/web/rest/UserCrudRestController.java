package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.UserDto;
import com.home.userservice.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserCrudRestController {

    private final UserService userService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDto delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto dto) {
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return userService.update(id, dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getOne(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserDto> getAll(@ModelAttribute UserFilter filter, Pageable pageable) {
        Page<UserDto> userDtos = userService.getAll(filter, pageable);
        return new PagedModel<>(userDtos);
    }
}

