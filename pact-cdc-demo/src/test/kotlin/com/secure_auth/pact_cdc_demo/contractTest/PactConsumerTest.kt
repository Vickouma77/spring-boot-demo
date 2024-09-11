package com.secure_auth.pact_cdc_demo.contractTest

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@ExtendWith(PactConsumerTestExt::class)
class OnboardingPactConsumerTest {

    @Pact(consumer = "PactTestConsumer", provider = "EmailVerificationProvider")
    fun requestNewEmailVerificationCodeSuccessPact(builder: PactDslWithProvider): V4Pact {
        return builder
            .given("User with valid token requests new email verification code")
            .uponReceiving("A POST request with valid Authorization header")
            .path("/onboarding/requestNewEmailVerificationCode")
            .method(HttpMethod.POST.name())
            .headers(mapOf(HttpHeaders.AUTHORIZATION to "Bearer valid-token"))
            .willRespondWith()
            .status(HttpStatus.OK.value())
            .body("Verification code sent successfully")
            .toPact(V4Pact::class.java)
    }

    @Test
    @PactTestFor(pactMethod = "requestNewEmailVerificationCodeSuccessPact")
    fun testRequestNewEmailVerificationCodeSuccess(mockServer: MockServer) {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders().apply {
            set(HttpHeaders.AUTHORIZATION, "Bearer valid-token")
        }
        val requestEntity = org.springframework.http.HttpEntity<Void>(headers)

        val response: ResponseEntity<String> = restTemplate.postForEntity(
            "${mockServer.getUrl()}/onboarding/requestNewEmailVerificationCode",
            requestEntity,
            String::class.java
        )

        assertEquals(HttpStatus.OK.value(), response.statusCodeValue)
        assertEquals("Verification code sent successfully", response.body)
    }
}
