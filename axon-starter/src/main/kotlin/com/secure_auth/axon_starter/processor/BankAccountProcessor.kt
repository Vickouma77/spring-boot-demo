package com.secure_auth.axon_starter.processor

import com.secure_auth.axon_starter.events.AccountCreatedEvent
import com.secure_auth.axon_starter.events.MoneyDepositedEvent
import com.secure_auth.axon_starter.query.model.BankAccountProjection
import com.secure_auth.axon_starter.query.repository.BankAccountRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class BankAccountProcessor(private val repository: BankAccountRepository) {

    @EventHandler
    fun on(event: AccountCreatedEvent) {
        repository.save(BankAccountProjection(event.accountId, event.initialBalance))
    }

    @EventHandler
    fun on(event: MoneyDepositedEvent) {
        val account = repository.findById(event.accountId).orElseThrow()
        account.balance = account.balance.plus(event.amount)
        repository.save(account)
    }
}