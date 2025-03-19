package com.example.user_service.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers("/user-service/users/**").permitAll()
            it.requestMatchers(PathRequest.toH2Console()).permitAll()
            it.requestMatchers("/user-service/welcome").permitAll()
            it.requestMatchers("/user-service/health_check").permitAll()
        }
        .headers { it.frameOptions { it.disable() } }
        .build()
}
