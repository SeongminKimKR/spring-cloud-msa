package com.example.user_service.dto

import java.util.Date

data class UserDto(
    val email: String,
    val name: String,
    val pwd: String,
    var userId: String = "",
    var createdAt: Date = Date(),
    var encryptedPwd: String = "",
)
