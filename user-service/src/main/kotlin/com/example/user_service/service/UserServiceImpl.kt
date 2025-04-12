package com.example.user_service.service

import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseOrder
import com.example.user_service.dto.ResponseUser
import com.example.user_service.repository.UserEntity
import com.example.user_service.repository.UserRepository
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val restTemplate: RestTemplate,
    private val env: Environment,
) : UserService {
    override fun createUser(request: RequestUser): ResponseUser {
        val user = request.toDomain(passwordEncoder)
        val entity = UserEntity.from(user)
        val savedEntity = userRepository.save(entity)

        return savedEntity.toResponse()
    }

    override fun getUserByUserId(userId: String): ResponseUser {
        val entity = userRepository.findByUserId(userId) ?: throw IllegalArgumentException("User not found")

        val orderUrl = String.format(env.getProperty("order_service.url")!!, userId)

        /*Using as rest template*/
        val orders: List<ResponseOrder> = restTemplate.exchange(orderUrl,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<ResponseOrder>>() {}
        ).body ?: throw IllegalArgumentException("Order is null")

        return entity.toResponse(orders)
    }

    override fun getUserByAll(): List<ResponseUser> = userRepository
        .findAll()
        .map { it.toResponse() }

    override fun getUserDetailsByEmail(email: String): ResponseUser {
        val entity = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("$email User Not Found")
        return entity.toResponse()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val entity = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("$username User Not Found")

        return User(
            username,
            entity.encryptedPwd,
            true,
            true,
            true,
            true,
            mutableListOf()
        )
    }
}
