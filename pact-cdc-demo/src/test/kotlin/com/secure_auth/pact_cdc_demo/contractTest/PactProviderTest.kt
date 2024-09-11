package com.secure_auth.pact_cdc_demo.contractTest


import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junitsupport.Consumer
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider
import com.secure_auth.pact_cdc_demo.EmailVerificationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("EmailVerificationProvider")
@Consumer("PactTestConsumer")
@PactBroker(url = "http://localhost:9292")
class PactProviderTest {
    @MockBean
    lateinit var emailVerificationService: EmailVerificationService

    @BeforeEach
    fun setup() {
        emailVerificationService = mock(EmailVerificationService::class.java)
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("User with valid token requests new email verification code")
    fun userWithValidTokenRequestsNewEmailVerificationCode() {
        val validToken = "b87f0324-50a6-4c61-94cc-b34593a1fd95"
        Mockito.`when`(emailVerificationService.sendVerificationCode(validToken)).thenReturn(true)
    }
}
