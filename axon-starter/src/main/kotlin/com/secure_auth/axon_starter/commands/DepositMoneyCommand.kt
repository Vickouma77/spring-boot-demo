package com.secure_auth.axon_starter.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
/**
 * Command to deposit money to a bank account.
 *
 * This command is sent to the {com.secure_auth.axon_starter.aggregate.BankAccountAggregate}
 * to initiate the creation of a new bank account. It contains the account ID and the initial balance
 * for the account.
 *
 * The [accountId] field is marked with [TargetAggregateIdentifier], which indicates
 * that this command targets the specific aggregate instance with the provided account ID.
 *
 * @param accountId The unique identifier for the bank account money is to deposited into.
 *
 * @see com.secure_auth.axon_starter.aggregate.BankAccountAggregate
 * @see com.secure_auth.axon_starter.events.AccountCreatedEvent
 * @since 1.0
 */
data class DepositMoneyCommand(
    @TargetAggregateIdentifier val accountId: String,
    val amount: Double
)
