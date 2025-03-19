package com.example.user_service.service

import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseUser

interface UserService {
    fun createUser(request: RequestUser): ResponseUser

    fun getUserByUserId(userId: String): ResponseUser

    fun getUserByAll(): List<ResponseUser>
}
