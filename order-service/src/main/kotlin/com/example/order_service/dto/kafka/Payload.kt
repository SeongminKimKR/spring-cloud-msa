package com.example.order_service.dto.kafka

data class Payload(
    val orderId: String,
    val userId: String,
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
)
