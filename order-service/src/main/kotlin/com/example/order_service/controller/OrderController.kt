package com.example.order_service.controller

import com.example.order_service.dto.RequestOrder
import com.example.order_service.dto.ResponseCreateOrder
import com.example.order_service.dto.ResponseOrder
import com.example.order_service.messagequeue.KafkaProducer
import com.example.order_service.service.OrderService
import org.slf4j.LoggerFactory
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
) {
    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @GetMapping("/health_check")
    fun status() = String.format(
        "It's Working in Order Service on PORT %s", env.getProperty("local.server.port")
    )

    @PostMapping("/{userId}/orders")
    fun createOrder(
        @PathVariable userId: String,
        @RequestBody request: RequestOrder,
    ): ResponseEntity<ResponseCreateOrder> {
        logger.info("Before add orders data")

        val response = orderService.createOrder(userId, request)

        logger.info("After added orders data")

        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{userId}/orders")
    fun getOrder(
        @PathVariable userId: String,
    ): ResponseEntity<List<ResponseOrder>> {
        logger.info("Before retrieve orders data")

        val response = orderService.getOrdersByUserId(userId)

        logger.info("After retrieve orders data")

        return ResponseEntity(response, HttpStatus.OK)
    }
}
