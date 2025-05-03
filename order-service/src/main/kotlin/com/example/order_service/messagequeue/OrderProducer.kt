package com.example.order_service.messagequeue

import com.example.order_service.domain.Order
import com.example.order_service.dto.kafka.Field
import com.example.order_service.dto.kafka.KafkaOrderDto
import com.example.order_service.dto.kafka.Payload
import com.example.order_service.dto.kafka.Schema
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    private val logger = LoggerFactory.getLogger(OrderProducer::class.java)

    private val fields = listOf(
        Field("string", true, "order_id"),
        Field("string", true, "user_id"),
        Field("string", true, "product_id"),
        Field("int32", true, "qty"),
        Field("int32", true, "unit_price"),
        Field("int32", true, "total_price"),
    )

    private val schema = Schema(
        type = "struct",
        fields = fields,
        optional = false,
        name = "orders"
    )

    fun send(
        topic: String,
        order: Order,
    ): Order {
        val payload = Payload(
            orderId = order.orderId,
            userId = order.userId,
            productId = order.productId,
            qty = order.qty,
            unitPrice = order.unitPrice,
            totalPrice = order.totalPrice
        )

        val kafkaOrderDto = KafkaOrderDto(schema, payload)

        val mapper = ObjectMapper()
        val jsonInString = try {
            mapper.writeValueAsString(kafkaOrderDto)
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
            ""
        }

        kafkaTemplate.send(topic, jsonInString)
        logger.info("Order Producer sent data from the Order microservice: $kafkaOrderDto")

        return order
    }

}
