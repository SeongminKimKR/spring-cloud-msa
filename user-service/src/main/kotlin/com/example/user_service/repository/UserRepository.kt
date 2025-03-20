package com.example.user_service.repository

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUserId(userId: String): UserEntity?
    fun findByEmail(username: String): UserEntity?
}
