package com.example.api_gateway_service

import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ApiGatewayServiceApplication {

	@Bean
	fun httpExchangeRepository() = InMemoryHttpExchangeRepository()
}

fun main(args: Array<String>) {
	runApplication<ApiGatewayServiceApplication>(*args)
}
