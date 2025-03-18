package com.example.user_service.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false, length = 50)
    val name: String,
    @Column(nullable = false, unique = true)
    val userId: String,
    @Column(nullable = false, unique = true)
    val encryptedPwd: String,
)
