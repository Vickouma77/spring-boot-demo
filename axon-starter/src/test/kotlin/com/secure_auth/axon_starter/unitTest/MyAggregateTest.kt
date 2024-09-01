package com.secure_auth.axon_starter.unitTest

import com.secure_auth.axon_starter.aggregate.BankAccountAggregate
import com.secure_auth.axon_starter.commands.CreateAccountCommand
import com.secure_auth.axon_starter.commands.DepositMoneyCommand
import com.secure_auth.axon_starter.events.AccountCreatedEvent
import com.secure_auth.axon_starter.events.MoneyDepositedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MyAggregateTest {

    private lateinit var fixture: AggregateTestFixture<BankAccountAggregate>

    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(BankAccountAggregate::class.java)
    }

    @Test
    fun `test account creation`(){
        val accountId = "account-123"
        val initialBalance = 0.0
        val command = CreateAccountCommand(accountId, initialBalance)
        val expectedEvent = AccountCreatedEvent(accountId, initialBalance)

        fixture.givenNoPriorActivity()
            .`when`(command)
            .expectSuccessfulHandlerExecution()
            .expectEvents(expectedEvent)
            .expectState { it.accountId == accountId }
    }

    @Test
    fun `test money deposited`(){
        val accountId = "account-123"
        val amount = 400.00
        val initialBalance = 0.0
        val accountCreatedEvent = AccountCreatedEvent(accountId, initialBalance)
        val command = DepositMoneyCommand(accountId, amount)
        val expectedEvent = MoneyDepositedEvent(accountId, amount)

        fixture.given(accountCreatedEvent)
            .`when`(command)
            .expectSuccessfulHandlerExecution()
            .expectEvents(expectedEvent)
    }

}