package com.secure_auth.pact_cdc_demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class EmailVerificationService(
    @Value("\${verification.token}") private val validToken: String
) {

    fun sendVerificationCode(token: String): VerificationResponse {
        return try {
            if (token == validToken) {
                VerificationResponse(
                    status = 200,
                    body = ResponseBody(
                        content = "Verification code sent successfully",
                        contentType = "text/plain"
                    )
                )
            } else {
                VerificationResponse(
                    status = 401,
                    body = ResponseBody(
                        content = "Failed to send verification code",
                        contentType = "text/plain"
                    )
                )
            }
        } catch (e: Exception) {
            VerificationResponse(
                status = 500,
                body = ResponseBody(
                    content = "Internal Server Error",
                    contentType = "text/plain"
                )
            )
        }
    }
}

data class VerificationResponse(
    val status: Int,
    val body: ResponseBody
)

data class ResponseBody(
    val content: String,
    val contentType: String
)
