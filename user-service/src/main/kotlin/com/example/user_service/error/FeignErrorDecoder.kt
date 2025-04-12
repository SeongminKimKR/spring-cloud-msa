package com.example.user_service.error

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception

@Component
class FeignErrorDecoder(
    private val env: Environment,
) : ErrorDecoder {
    override fun decode(
        methodKey: String,
        response: Response,
    ): Exception? {
        return when(response.status()) {
            400 -> null
            404 -> {
                if (methodKey.contains("getOrders"))
                    ResponseStatusException(HttpStatus.valueOf(
                        response.status()), env.getProperty("order_service.exception.orders_is_empty")
                    )
                else
                    null            }
            else -> {
                Exception(response.reason())
            }
        }
    }
}
