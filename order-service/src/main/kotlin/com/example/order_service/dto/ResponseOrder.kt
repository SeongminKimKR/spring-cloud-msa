package com.example.order_service.dto

import java.util.Date

data class ResponseCreateOrder(
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val orderId: String
)

data class ResponseOrder(
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val orderId: String,
    val createdAt: Date,
)
