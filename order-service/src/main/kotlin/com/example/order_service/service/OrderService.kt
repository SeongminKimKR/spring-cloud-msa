package com.example.order_service.service

import com.example.order_service.dto.RequestOrder
import com.example.order_service.dto.ResponseCreateOrder
import com.example.order_service.dto.ResponseOrder

interface OrderService {
    fun createOrder(
        userId: String,
        request: RequestOrder,
    ): ResponseCreateOrder

    fun getOrderByOrderId(orderId: String): ResponseOrder

    fun getOrdersByUserId(userId: String): List<ResponseOrder>
}
