package com.home.userservice.infrastucture.web.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("/api/rest/v1/users/")
@AllArgsConstructor
public class UserCrudRestController {

    @GetMapping("{id}")
    public UserResponseGetDto getUserById(@PathVariable Long id) {

    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable Long id) {

    }

    @PostMapping
    public UserResponsePostDto createUser(@RequestBody UserRequestPostDto userRequestPostDto) {

    }

    @PutMapping("{id}")
    public UserResponsePutDto updateUserById(@PathVariable Long id,
                                             @RequestBody UserRequestPutDto userRequestPutDto) {

    }

    @PatchMapping("{id}")
    public UserResponsePatchDto updateUserById(@PathVariable Long id,
                                               @RequestBody UserRequestPatchDto userRequestPatchDto) {

    }
}
