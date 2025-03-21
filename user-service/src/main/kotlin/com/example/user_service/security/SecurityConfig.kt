package com.example.user_service.security

import com.example.user_service.service.UserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.util.matcher.IpAddressMatcher
import java.util.function.Supplier


@Configuration
class SecurityConfig(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val userService: UserService,
    private val env: Environment,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authenticationManager(http)

        return http.csrf { it.disable() }
            .authorizeHttpRequests {
//            it.requestMatchers("/user-service/users/**").permitAll()
                it.requestMatchers("/users", "POST")
                it.requestMatchers("/**")
                    .access(WebExpressionAuthorizationManager("hasIpAddress('192.168.219.103')"))
                it.requestMatchers(PathRequest.toH2Console()).permitAll()
            }
            .authenticationManager(authenticationManager)
            .addFilter(getAuthenticationFilter(authenticationManager))
            .headers { it.frameOptions { it.disable() } }
            .build()
    }

    private fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)

        authenticationManagerBuilder.userDetailsService<UserDetailsService>(userService)
            .passwordEncoder(bCryptPasswordEncoder)

        return authenticationManagerBuilder.build()
    }

    private fun hasIpAddress(context: RequestAuthorizationContext): AuthorizationDecision =
        AuthorizationDecision(ALLOWED_IP_ADDRESS_MATCHER.matches(context.request))

    private fun getAuthenticationFilter(authenticationManager: AuthenticationManager): AuthenticationFilter {
        val authenticationFilter = AuthenticationFilter()
        authenticationFilter.setAuthenticationManager(authenticationManager)

        return authenticationFilter
    }

    companion object {
        private const val ALLOWED_IP_ADDRESS: String = "127.0.0.1"
        private const val SUBNET: String = "/32"
        private val ALLOWED_IP_ADDRESS_MATCHER: IpAddressMatcher = IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET)
    }
}
