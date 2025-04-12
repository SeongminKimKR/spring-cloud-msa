package com.example.user_service.dto

import java.util.Date

data class ResponseOrder(
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val createdAt: Date,
    val orderId: String,
)

