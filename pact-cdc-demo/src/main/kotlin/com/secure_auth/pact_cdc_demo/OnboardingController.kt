package com.secure_auth.pact_cdc_demo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/onboarding")
class OnboardingController(private val emailVerificationService: EmailVerificationService) {

    @PostMapping("/requestNewEmailVerificationCode")
    fun requestNewEmailVerificationCode(
        @RequestHeader("Authorization") authorizationHeader: String): ResponseEntity<String>
    {
        val token = extractBearerToken(authorizationHeader)
        return if (emailVerificationService.sendVerificationCode(token)) {
            ResponseEntity("Verification code sent successfully", HttpStatus.OK)
        } else {
            ResponseEntity("Failed to send verification code", HttpStatus.UNAUTHORIZED)
        }
    }

    private fun extractBearerToken(authorizationHeader: String): String {
        return authorizationHeader.removePrefix("Bearer ").trim()
    }

    private val users = listOf(
        User(1, "John Doe", "john.doe@example.com"),
        User(2, "Jane Smith", "jane.smith@example.com")
    )

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = users.find { it.id == id }
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    data class User(val id: Long, val name: String, val email: String)
}