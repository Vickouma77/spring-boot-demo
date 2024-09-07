package com.secure_auth.pact_cdc_demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    private val users = listOf(
        User(1, "John Doe", "john.doe@example.com"),
        User(2, "Jane Smith", "jane.smith@example.com")
    )

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: Long): User? {
        return users.find { it.id == id }
    }
}

data class User(val id: Long, val name: String, val email: String)