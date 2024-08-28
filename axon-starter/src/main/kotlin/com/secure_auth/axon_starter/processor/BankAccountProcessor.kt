package com.secure_auth.axon_starter.processor

import com.secure_auth.axon_starter.events.AccountCreatedEvent
import com.secure_auth.axon_starter.events.MoneyDepositedEvent
import com.secure_auth.axon_starter.query.model.BankAccountProjection
import com.secure_auth.axon_starter.query.repository.BankAccountRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
/**
 * Processor responsible for handling events related to bank accounts and updating the query model.
 *
 * The [BankAccountProcessor] listens to events such as [AccountCreatedEvent] and
 * [MoneyDepositedEvent] and updates the corresponding projections in the database.
 *
 * This processor uses the [BankAccountRepository] to save and update [BankAccountProjection]
 * objects, which represent the state of bank accounts in a read-optimized format.
 *
 * Each event handler method in this class is annotated with {EventHandler}, indicating that it will
 * be triggered when the respective event is published by the event bus.
 *
 * @see AccountCreatedEvent
 * @see MoneyDepositedEvent
 * @see BankAccountProjection
 * @see BankAccountRepository
 * @since 1.0
 */
@Component
class BankAccountProcessor(private val repository: BankAccountRepository) {

    /**
     * Event handler for the {AccountCreatedEvent}.
     *
     * When a new bank account is created, this handler saves a {BankAccountProjection}
     * with the account ID and initial balance to the repository.
     *
     *
     * @param event The event containing the account creation details.
     */
    @EventHandler
    fun on(event: AccountCreatedEvent) {
        repository.save(BankAccountProjection(event.accountId, event.initialBalance))
    }

    /**
     * Event handler for the {MoneyDepositedEvent}.
     *
     * When money is deposited into a bank account, this handler retrieves the corresponding
     * {BankAccountProjection} from the repository, updates its balance, and saves the updated projection.
     *
     * @param event The event containing the deposit details.
     * @throws NoSuchElementException if the account cannot be found in the repository.
     */
    @EventHandler
    fun on(event: MoneyDepositedEvent) {
        val account = repository.findById(event.accountId).orElseThrow()
        account.balance = account.balance.plus(event.amount)
        repository.save(account)
    }
}