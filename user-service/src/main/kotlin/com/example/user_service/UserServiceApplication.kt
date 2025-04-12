package com.example.user_service

import feign.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class UserServiceApplication {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate()

    @Bean
    fun feignLoggerLevel() = Logger.Level.FULL
}

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}
