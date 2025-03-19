package com.example.order_service.service

import com.example.order_service.dto.RequestOrder
import com.example.order_service.dto.ResponseCreateOrder
import com.example.order_service.dto.ResponseOrder
import com.example.order_service.repository.OrderEntity
import com.example.order_service.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
) : OrderService{
    override fun createOrder(
        userId: String,
        request: RequestOrder,
    ): ResponseCreateOrder {
        val order = request.toDomain(userId)
        val entity = OrderEntity.from(order)
        val savedEntity = orderRepository.save(entity)

        return savedEntity.toCreateResponse()
    }

    override fun getOrderByOrderId(orderId: String): ResponseOrder =
        orderRepository.findByOrderId(orderId)?.toResponse()
            ?: throw IllegalArgumentException("Not found order")

    override fun getOrdersByUserId(userId: String): List<ResponseOrder> = orderRepository
        .findByUserId(userId)
        .map { it.toResponse() }
}
