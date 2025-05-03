package com.example.order_service.dto.kafka

data class Schema(
    val type: String,
    val fields: List<Field>,
    val optional: Boolean,
    val name: String,
)
