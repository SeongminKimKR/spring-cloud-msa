package com.example.order_service.dto.kafka

import java.io.Serializable

data class KafkaOrderDto(
    val schema: Schema,
    val payload: Payload,
): Serializable
