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

data class MoneyWithdrawnEvent(
    val accountId: String,
    val amountToWithdraw: Double
)

data class InitiatedTransferEvent(
    val transactionId: String,
    val sourceAccountId: String,
    val targetAccountId: String,
    val amount: Double
)

data class MoneyDebitEvent(
    val transactionId: String,
    val accountId: String,
    val amount: Double
)

data class CreditedAccountEvent(
    val transactionId: String,
    val accountId: String,
    val amount: Double
)

data class TransactionFailedEvent(
    val transactionId: String,
    val reason: String
)