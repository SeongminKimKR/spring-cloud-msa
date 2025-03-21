package com.example.user_service.security

import com.example.user_service.dto.RequestLogin
import com.example.user_service.service.UserService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.Date

class AuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val env: Environment,
) : UsernamePasswordAuthenticationFilter(){
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?,
    ): Authentication {
        val credential = runCatching {
            jacksonObjectMapper().readValue(request.inputStream, RequestLogin::class.java)
        }.getOrElse {
            throw RuntimeException("Invalid login request", it)
        }

        return authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(credential.email, credential.password, mutableListOf()))
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication,
    ) {
        val user = authResult.principal as User
        val username = user.username
        val userDetails = userService.getUserDetailsByEmail(username)
        val expirationTime = env.getProperty("token.expiration_time")?.toLong() ?: throw IllegalStateException("Not exist token_expiration_time")
        val secret = env.getProperty("token.secret") ?: throw IllegalStateException("Not exist token_expiration_time")
        val key = Keys.hmacShaKeyFor(secret.toByteArray())

        val token = Jwts.builder()
            .subject(userDetails.userId)
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(key)
            .compact()

        response.addHeader("token", token)
        response.addHeader("userId", userDetails.userId)
    }
}
