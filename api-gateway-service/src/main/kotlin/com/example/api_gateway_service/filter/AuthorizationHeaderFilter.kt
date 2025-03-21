package com.example.api_gateway_service.filter

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(
    private val env: Environment
) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {
    private val logger = LoggerFactory.getLogger(AuthorizationHeaderFilter::class.java)

    class Config

    override fun apply(config: Config): GatewayFilter {
        val filter = GatewayFilter({ exchange, chain ->
            val request = exchange.request

            if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "No authorization header")
            }

            val authorizationHeader = request.headers.get(HttpHeaders.AUTHORIZATION)?.get(0)
                ?: throw IllegalStateException("Not exist authorization")

            val jwt = authorizationHeader.replace("Bearer ", "")

            if (!isJwtValid(jwt)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid")
            }
            chain.filter(exchange)
        })

        return filter
    }

    private fun isJwtValid(jwt: String): Boolean {
        val secret = env.getProperty("token.secret") ?: throw IllegalStateException("Not exist token_expiration_time")
        val key = Keys.hmacShaKeyFor(secret.toByteArray())

        val subject = runCatching {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt) // ✅ 최신 방식
                .payload.subject
        }.getOrElse {
            return false
        }

        return !subject.isNullOrEmpty()
    }

    private fun onError(
        exchange: ServerWebExchange,
        err: String,
    ): Mono<Void> {
        val response = exchange.response
        response.setStatusCode(HttpStatus.UNAUTHORIZED)
        logger.error(err)

        return response.setComplete()
    }

}
