package com.secure_auth.axon_starter.unitTest

import com.secure_auth.axon_starter.commands.CreditAccountCommand
import com.secure_auth.axon_starter.commands.DebitAccountCommand
import com.secure_auth.axon_starter.events.CreditedAccountEvent
import com.secure_auth.axon_starter.events.InitiatedTransferEvent
import com.secure_auth.axon_starter.events.MoneyDebitEvent
import com.secure_auth.axon_starter.events.TransactionFailedEvent
import com.secure_auth.axon_starter.sagas.MoneyTransferSaga
import org.axonframework.test.saga.SagaTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class MySagaTest {

    private lateinit var fixture: SagaTestFixture<MoneyTransferSaga>

    @BeforeEach
    fun setUp() {
        fixture = SagaTestFixture(MoneyTransferSaga::class.java)
    }

//    @Test
//    fun `test successful money transfer saga`() {
//        val transactionId = UUID.randomUUID().toString()
//        val sourceAccountId = "source-123"
//        val targetAccountId = "target-456"
//        val amount = 100.0
//
//        // Given that a money transfer is initiated
//        fixture.givenNoPriorActivity()
//            .whenAggregate(transactionId)
//
//            .publishes(InitiatedTransferEvent(transactionId, sourceAccountId, targetAccountId, amount))
//
//            .expectDispatchedCommands(DebitAccountCommand(sourceAccountId, transactionId, amount))
//
//            .whenPublishingA(MoneyDebitEvent(transactionId, sourceAccountId, amount))
//
//            .expectDispatchedCommands(CreditAccountCommand(targetAccountId, transactionId, amount))
//
//            .whenPublishingA(CreditedAccountEvent(transactionId, targetAccountId, amount))
//
//            .expectActiveSagas(0)
//
//            .expectNoDispatchedCommands()
//    }
//
//    @Test
//    fun `test failed money transfer saga`() {
//        val transactionId = UUID.randomUUID().toString()
//        val sourceAccountId = "source-123"
//        val targetAccountId = "target-456"
//        val amount = 100.0
//        val failureReason = "Insufficient funds"
//
//        // Given that a money transfer is initiated
//        fixture.givenNoPriorActivity()
//            .whenAggregate(transactionId)
//
//            .publishes(InitiatedTransferEvent(transactionId, sourceAccountId, targetAccountId, amount))
//
//            .expectDispatchedCommands(DebitAccountCommand(sourceAccountId, transactionId, amount))
//
//            .whenPublishingA(TransactionFailedEvent(transactionId, failureReason))
//
//            .expectActiveSagas(0)
//            .expectNoDispatchedCommands()
//    }
}