package com.example.user_service.controller

import com.example.user_service.dto.RequestUser
import com.example.user_service.dto.ResponseUser
import com.example.user_service.service.UserService
import com.example.user_service.vo.Greeting
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(
    private val greeting: Greeting,
    private val userService: UserService,
    private val env:Environment,
) {

    @GetMapping("/health_check")
    fun status() = String.format("It's Working in User Service on PORT %s"
        , env.getProperty("local.server.port"))

    @GetMapping("/welcome")
    fun welcome() = greeting.message

    @PostMapping("/users")
    fun createUser(
        @RequestBody request: RequestUser
    ): ResponseEntity<ResponseUser> {
        val response = userService.createUser(request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<ResponseUser>> {
        val response = userService.getUserByAll()
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<ResponseUser> {
        val response = userService.getUserByUserId(userId)
        return ResponseEntity(response, HttpStatus.OK)
    }
}
