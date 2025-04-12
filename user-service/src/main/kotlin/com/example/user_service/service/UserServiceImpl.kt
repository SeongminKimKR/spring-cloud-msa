package com.example.user_service.service

import com.example.user_service.client.OrderServiceClient
import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseUser
import com.example.user_service.repository.UserEntity
import com.example.user_service.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val orderServiceClient: OrderServiceClient,
//    private val restTemplate: RestTemplate,
//    private val env: Environment,
) : UserService {
    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun createUser(request: RequestUser): ResponseUser {
        val user = request.toDomain(passwordEncoder)
        val entity = UserEntity.from(user)
        val savedEntity = userRepository.save(entity)

        return savedEntity.toResponse()
    }

    override fun getUserByUserId(userId: String): ResponseUser {
        val entity = userRepository.findByUserId(userId) ?: throw IllegalArgumentException("User not found")

//        val orderUrl = String.format(env.getProperty("order_service.url")!!, userId)
//
//        /*Using as rest template*/
//        val orders: List<ResponseOrder> = restTemplate.exchange(orderUrl,
//            HttpMethod.GET,
//            null,
//            object : ParameterizedTypeReference<List<ResponseOrder>>() {}
//        ).body ?: throw IllegalArgumentException("Order is null")

//        val orders = try {
//            orderServiceClient.getOrders(userId)
//        } catch (e: FeignException) {
//            logger.error(e.message)
//            emptyList()
//        }

        val orders = orderServiceClient.getOrders(userId)

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
