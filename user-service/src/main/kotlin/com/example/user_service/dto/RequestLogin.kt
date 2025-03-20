package com.example.user_service.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RequestLogin(
    @field:NotNull(message = "Email cannot be null")
    @field:Size(min = 2, message = "Email not be less than 2 characters")
    @Email
    val email: String,

    @field:NotNull(message = "Password cannot be null")
    @field:Size(min = 8, message = "Password must be equal or greater than 8 characters")
    val password: String,
)
