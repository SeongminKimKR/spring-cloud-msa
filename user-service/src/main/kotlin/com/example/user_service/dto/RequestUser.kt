package com.example.user_service.dto

import com.example.user_service.domain.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class RequestUser(
    @field:NotNull(message = "Email cannot be null")
    @field:Min(value = 2, message = "Email not be less than 2 characters")
    @Email
    val email: String,

    @field:NotNull(message = "name cannot be null")
    @field:Size(min = 2, message = "name not be less than 2 characters")
    val name: String,

    @field:NotNull(message = "Password cannot be null")
    @field:Size(min = 8, message = "Password must be equal or greater than 8 characters")
    val pwd: String,
) {
    fun toDomain() = User (
        email = email,
        name = name,
        pwd = pwd,
    )
}
