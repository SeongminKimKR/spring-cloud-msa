package com.example.user_service.service

import com.example.user_service.dto.UserDto
import com.example.user_service.jpa.UserEntity
import com.example.user_service.jpa.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun createUser(userDto: UserDto): UserDto {
        userDto.userId = UUID.randomUUID().toString()
        userDto.encryptedPwd = "encrypted_password"

        val entity = UserEntity(
            email = userDto.email,
            name = userDto.name,
            userId = userDto.userId,
            encryptedPwd = userDto.encryptedPwd
        )

        userRepository.save(entity)

        return userDto
    }
}
