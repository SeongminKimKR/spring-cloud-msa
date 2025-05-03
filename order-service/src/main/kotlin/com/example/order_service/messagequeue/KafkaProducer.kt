package com.example.order_service.messagequeue

import com.example.order_service.domain.Order
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    private val logger = LoggerFactory.getLogger(KafkaProducer::class.java)

    fun send(
        topic: String,
        order: Order
    ): Order {
        val mapper = ObjectMapper()
        val jsonInString = try {
            mapper.writeValueAsString(order)
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
            ""
        }

        kafkaTemplate.send(topic, jsonInString)
        logger.info("Kafka Producer sent data from the Order microservice: $order")

        return order
    }
}
