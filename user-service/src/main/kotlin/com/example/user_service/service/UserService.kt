package com.example.user_service.service

import com.example.user_service.dto.UserDto

interface UserService {
    fun createUser(userDto: UserDto): UserDto
}
