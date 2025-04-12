package com.example.user_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableDiscoveryClient
class UserServiceApplication {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate()
}

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}
