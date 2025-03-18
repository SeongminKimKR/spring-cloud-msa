package com.example.user_service.domain

import java.util.*

class User (
    val email: String,
    val name: String,
    val pwd: String,
    val userId: String = UUID.randomUUID().toString(),
    val createdAt: Date = Date(),
    val encryptedPwd: String,
)
