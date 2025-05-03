package com.example.order_service.dto.kafka

data class Field(
    val type: String,
    val optional: Boolean,
    val field: String,
)
