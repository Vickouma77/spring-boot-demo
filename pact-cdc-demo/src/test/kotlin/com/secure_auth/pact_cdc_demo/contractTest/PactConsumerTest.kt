package com.secure_auth.pact_cdc_demo.contractTest

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.client.RestTemplate


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "PactTestProvider")
class PactConsumerTest {

    @Pact(consumer = "PactTestConsumer")
    fun getUserByIdPact(builder: PactDslWithProvider): RequestResponsePact {
        return builder
            .given("User with ID 1 exists")
            .uponReceiving("A request for user with ID 1")
            .path("/users/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(
                PactDslJsonBody()
                    .numberType("id", 1)
                    .stringType("name", "John Doe")
                    .stringType("email", "john.doe@example.com")
            )
            .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "getUserByIdPact")
    fun testGetUserById(mockServer: MockServer) {
        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity("${mockServer.getUrl()}/users/1", User::class.java)

        assertEquals(200, response.statusCodeValue)
        val user = response.body
        assertEquals(1, user?.id)
        assertEquals("John Doe", user?.name)
        assertEquals("john.doe@example.com", user?.email)
    }

    data class User(val id: Long, val name: String, val email: String)
}