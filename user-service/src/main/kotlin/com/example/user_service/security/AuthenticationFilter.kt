package com.example.user_service.security

import com.example.user_service.dto.RequestLogin
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter : UsernamePasswordAuthenticationFilter(){
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?,
    ): Authentication {
        val credential = runCatching {
            ObjectMapper().readValue(request.inputStream, RequestLogin::class.java)
        }.getOrElse {
            throw RuntimeException("Invalid login request", it)
        }

        return authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(credential.email, credential.password, mutableListOf()))
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?,
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
    }
}
