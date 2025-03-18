package com.example.api_gateway_service.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GlobalFilter : AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {
    private val logger = LoggerFactory.getLogger(CustomFilter::class.java)

    override fun apply(config: Config): GatewayFilter {
        val filter = OrderedGatewayFilter({ exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            logger.info("Logging Filter baseMessage: ${config.baseMessage}")

            if (config.preLogger) {
                logger.info("Logging PRE Filter Start: request id -> ${request.id}")
            }
            chain.filter(exchange).then(Mono.fromRunnable {
                if (config.postLogger) {
                    logger.info("Logging Post Filter End: response code -> ${response.statusCode}")
                }
            })
        }, Ordered.HIGHEST_PRECEDENCE)

        return filter
    }

    data class Config(
        val baseMessage: String,
        val preLogger: Boolean,
        val postLogger: Boolean,
    )
}
