package com.home.userservice.domain.service;

public interface UserService {

    UserResponseGetDto getUserById(Long id);

    void deleteUserById(Long id);

    UserResponsePostDto createUser(UserRequestPostDto userRequestPostDto);

    UserResponsePutDto updateUserById(Long id, UserRequestPutDto userRequestPutDto);

    UserResponsePatchDto updateUserById(Long id, UserRequestPatchDto userRequestPatchDto);
}
