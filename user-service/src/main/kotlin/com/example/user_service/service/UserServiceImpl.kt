package com.example.user_service.service

import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseUser
import com.example.user_service.repository.UserEntity
import com.example.user_service.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) : UserService {
    override fun createUser(request: RequestUser): ResponseUser {
        val user = request.toDomain(passwordEncoder)
        val entity = UserEntity.from(user)
        val savedEntity = userRepository.save(entity)

        return savedEntity.toResponse()
    }
}
