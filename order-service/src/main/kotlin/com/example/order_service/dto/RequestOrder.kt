package com.example.order_service.dto

import com.example.order_service.domain.Order

data class RequestOrder(
    val productId: String,
    val qty: Int,
    val unitPrice: Int
) {
    fun toDomain(userId: String) = Order(
        qty = qty,
        unitPrice = unitPrice,
        totalPrice = unitPrice * qty,
        productId = productId,
        userId = userId,
    )
}
