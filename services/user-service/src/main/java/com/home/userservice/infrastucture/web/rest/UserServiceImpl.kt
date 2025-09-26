package com.home.userservice.infrastucture.web.rest;

import com.home.userservice.domain.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override fun getUserById(id: Long?): UserResponseGetDto {
        TODO("Not yet implemented")
    }

    override fun deleteUserById(id: Long?) {
        TODO("Not yet implemented")
    }

    override fun createUser(userRequestPostDto: UserRequestPostDto): UserResponsePostDto {
        TODO("Not yet implemented")
    }

    override fun updateUserById(id: Long?, userRequestPutDto: UserRequestPutDto): UserResponsePutDto {
        TODO("Not yet implemented")
    }

    override fun updateUserById(id: Long?, userRequestPatchDto: UserRequestPatchDto): UserResponsePatchDto {
        TODO("Not yet implemented")
    }
}