package com.example.user_service.service

import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseUser
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun createUser(request: RequestUser): ResponseUser

    fun getUserByUserId(userId: String): ResponseUser

    fun getUserByAll(): List<ResponseUser>

    fun getUserDetailsByEmail(email: String): ResponseUser
}
