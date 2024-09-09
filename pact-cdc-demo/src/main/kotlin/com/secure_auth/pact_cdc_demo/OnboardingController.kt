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
}