package com.secure_auth.pact_cdc_demo.consumer


import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTest
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.apache.hc.client5.http.fluent.Request
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.IOException
import kotlin.jvm.Throws
import kotlin.test.assertNotNull

@SpringBootTest
@PactConsumerTest
@PactTestFor(providerName = "Provider")
class ConsumerPactTest {

    @Pact(consumer = "TestConsumer")
    fun createPact(builder: PactDslWithProvider): RequestResponsePact {
        return builder
            .given("test_state")
            .uponReceiving("GET request")
                .path("/article/")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(
                """
                    {
                        "id": "1",
                        "title": "Pact done by Preston",
                        "content": "Pact is a contract testing tool."
                    }
                """.trimIndent())
            .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    @Throws(IOException::class)
    fun testGetArticle(mockServer: MockServer) {

        val url = "${mockServer.getUrl()} + /article"
        val httpResponse = Request.get(url).execute().returnContent().asString()

        assertNotNull(httpResponse)

        val jsonResponse = JSONObject(httpResponse)
        assertEquals("1", jsonResponse.getString("id"))
        assertEquals("Pact done by Preston", jsonResponse.getString("title"))
        assertEquals("Pact is a contract testing tool.", jsonResponse.getString("content"))
    }
}