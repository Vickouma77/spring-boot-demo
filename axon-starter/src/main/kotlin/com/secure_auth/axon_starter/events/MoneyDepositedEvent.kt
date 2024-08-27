package com.secure_auth.axon_starter.events

/**
 * Event representing a deposit of money into a bank account.
 *
 * This event is raised when a deposit is made to a bank account. It contains the account ID
 * to which the deposit was made and the amount deposited.
 *
 *
 * In an event-sourced system, this event will be used to update the state of the bank account
 * aggregate by increasing the balance by the deposited amount.
 *
 * @param accountId The unique identifier of the bank account where the deposit occurred.
 * @param amount The amount of money deposited into the account.
 *
 * @see com.secure_auth.axon_starter.aggregate.BankAccountAggregate
 * @see com.secure_auth.axon_starter.commands.DepositMoneyCommand
 * @since 1.0
 */

data class MoneyDepositedEvent(
    val accountId: String,
    val amount: Double
)
