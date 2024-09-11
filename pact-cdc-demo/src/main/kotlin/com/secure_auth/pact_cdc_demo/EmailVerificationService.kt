package com.secure_auth.pact_cdc_demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class EmailVerificationService(
    @Value("\${verification.token}") private val validToken: String
) {

    fun sendVerificationCode(token: String): Boolean {

        return token == validToken
    }
}