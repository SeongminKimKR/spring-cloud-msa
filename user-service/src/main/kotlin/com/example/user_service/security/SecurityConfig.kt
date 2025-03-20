package com.example.user_service.security

import com.example.user_service.service.UserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val userService: UserService,
    private val env: Environment,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authenticationManager(http)

        return http.csrf { it.disable() }
            .authorizeHttpRequests {
//            it.requestMatchers("/user-service/users/**").permitAll()
                it.requestMatchers("/**")
                it.requestMatchers(PathRequest.toH2Console()).permitAll()
            }
            .authenticationManager(authenticationManager)
            .addFilter(getAuthenticationFilter(authenticationManager))
            .headers { it.frameOptions { it.disable() } }
            .build()
    }

    private fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)

        authenticationManagerBuilder.userDetailsService<UserDetailsService>(userService)
            .passwordEncoder(bCryptPasswordEncoder)

        return authenticationManagerBuilder.build()
    }

    private fun getAuthenticationFilter(authenticationManager: AuthenticationManager): AuthenticationFilter {
        val authenticationFilter = AuthenticationFilter()
        authenticationFilter.setAuthenticationManager(authenticationManager)

        return authenticationFilter
    }
}
