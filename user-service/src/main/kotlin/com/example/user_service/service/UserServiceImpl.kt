package com.example.user_service.service

import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseUser
import com.example.user_service.jpa.UserEntity
import com.example.user_service.jpa.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun createUser(request: RequestUser): ResponseUser {
        val user = request.toDomain()
        val entity = UserEntity.from(user)
        val savedEntity = userRepository.save(entity)

        return savedEntity.toResponse()
    }
}
