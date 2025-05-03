package com.example.order_service.controller

import com.example.order_service.dto.RequestOrder
import com.example.order_service.dto.ResponseCreateOrder
import com.example.order_service.dto.ResponseOrder
import com.example.order_service.messagequeue.KafkaProducer
import com.example.order_service.service.OrderService
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order-service/")
class OrderController(
    private val orderService: OrderService,
    private val env: Environment,
    private val kafkaProducer: KafkaProducer,
) {
    @GetMapping("/health_check")
    fun status() = String.format(
        "It's Working in Order Service on PORT %s", env.getProperty("local.server.port")
    )

    @PostMapping("/{userId}/orders")
    fun createOrder(
        @PathVariable userId: String,
        @RequestBody request: RequestOrder,
    ): ResponseEntity<ResponseCreateOrder> {
        val response = orderService.createOrder(userId, request)

        kafkaProducer.send("example-catalog-topic", request.toDomain(userId))

        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{userId}/orders")
    fun getOrder(
        @PathVariable userId: String,
    ): ResponseEntity<List<ResponseOrder>> {
        val response = orderService.getOrdersByUserId(userId)
        return ResponseEntity(response, HttpStatus.OK)
    }
}
