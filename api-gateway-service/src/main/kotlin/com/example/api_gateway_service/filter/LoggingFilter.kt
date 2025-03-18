package com.example.api_gateway_service.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoggingFilter : AbstractGatewayFilterFactory<LoggingFilter.Config>(Config::class.java){
    private val logger = LoggerFactory.getLogger(CustomFilter::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter{ exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            logger.info("Global Filter baseMessage: ${config.baseMessage}")

            if(config.preLogger) {
                logger.info("Global Filter Start: request id -> ${request.id}")
            }
            chain.filter(exchange).then(Mono.fromRunnable {
                if(config.postLogger) {
                    logger.info("Global Filter End: response code -> ${response.statusCode}")
                }

            })

        }
    }

    data class Config(
        val baseMessage:String,
        val preLogger: Boolean,
        val postLogger: Boolean,
    )
}
