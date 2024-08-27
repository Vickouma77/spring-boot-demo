package com.secure_auth.axon_starter.events

/**
 * Event representing the creation of a new bank account.
 *
 * This event is raised when a new bank account is created. It contains the account ID and the initial
 * balance of the account.
 *
 *
 * In an event-sourced system, this event will be used to initialize the state of the bank account
 * aggregate with the provided account ID and starting balance.
 *
 * @param accountId The unique identifier of the newly created bank account.
 * @param initialBalance The initial balance of the bank account.
 *
 * @see com.secure_auth.axon_starter.aggregate.BankAccountAggregate
 * @see com.secure_auth.axon_starter.commands.CreateAccountCommand
 * @since 1.0
 */

data class AccountCreatedEvent(
    val accountId: String,
    val initialBalance: Double
)
