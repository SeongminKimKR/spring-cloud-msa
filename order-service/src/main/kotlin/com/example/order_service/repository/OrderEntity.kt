package com.example.order_service.repository

import com.example.order_service.domain.Order
import com.example.order_service.dto.ResponseCreateOrder
import com.example.order_service.dto.ResponseOrder
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "orders")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false, length = 120, unique = true)
    val productId: String,
    @Column(nullable = false)
    val qty: Int,
    @Column(nullable = false)
    val unitPrice: Int,
    @Column(nullable = false)
    val totalPrice: Int,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false, unique = true)
    val orderId: String,
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    val createdAt: Date? = null
) : Serializable {
    fun toCreateResponse() = ResponseCreateOrder(
        orderId = orderId,
        productId = productId,
        qty = qty,
        unitPrice = unitPrice,
        totalPrice = totalPrice,
    )

    fun toResponse() = ResponseOrder(
        orderId = orderId,
        productId = productId,
        qty = qty,
        unitPrice = unitPrice,
        totalPrice = totalPrice,
        createdAt = createdAt ?: throw IllegalArgumentException(),
    )

    companion object {
        fun from(order: Order) = OrderEntity(
            orderId = order.orderId,
            productId = order.productId,
            qty = order.qty,
            unitPrice = order.unitPrice,
            totalPrice = order.totalPrice,
            userId = order.userId,
        )
    }
}
