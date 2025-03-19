package com.example.order_service.repository

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, Long> {
    fun findByOrderId(orderId: String): OrderEntity?

    fun findByUserId(userId: String): List<OrderEntity>
}
