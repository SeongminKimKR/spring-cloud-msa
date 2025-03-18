package com.example.user_service.controller

import com.example.user_service.dto.UserDto
import com.example.user_service.service.UserService
import com.example.user_service.vo.Greeting
import com.example.user_service.vo.RequestUser
import com.example.user_service.vo.ResponseUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(
    private val greeting: Greeting,
    private val userService: UserService,
) {

    @GetMapping("/health_check")
    fun status() = "It's Working in User Service"

    @GetMapping("/welcome")
    fun welcome() = greeting.message

    @PostMapping("/users")
    fun createUser(
        @RequestBody request: RequestUser
    ): ResponseEntity<ResponseUser> {
        val dto = UserDto(
            email = request.email,
            name = request.name,
            pwd = request.pwd,
        )

        val result = userService.createUser(dto)

        val response = ResponseUser(
            email = result.email,
            name = request.name,
            userId = result.userId
        )

        return ResponseEntity<ResponseUser>(response, HttpStatus.CREATED)
    }
}
