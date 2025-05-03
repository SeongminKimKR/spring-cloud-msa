package com.example.catalog_service.messagequeue

import com.example.catalog_service.respository.CatalogRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val repository: CatalogRepository,
) {
    private val logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["example-catalog-topic"])
    fun updateQty(kafkaMessage: String) {
        logger.info("Kafka Message: -> $kafkaMessage")
        val mapper = ObjectMapper()
        val map: Map<Any, Any> = try {
            mapper.readValue(kafkaMessage)
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
            emptyMap()  // 에러 시 빈 Map 반환
        }

        repository.findByProductId(map["productId"].toString())?.let {
            it.updateStock(it.stock - map["qty"] as Int)
            repository.save(it)
        }
    }
}
