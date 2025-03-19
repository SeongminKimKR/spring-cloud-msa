package com.example.order_service.domain

import java.io.Serializable
import java.util.UUID

class Order(
    val orderId: String = UUID.randomUUID().toString(),
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val userId: String,
) : Serializable
