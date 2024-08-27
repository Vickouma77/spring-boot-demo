package com.secure_auth.axon_starter.aggregate;

import com.secure_auth.axon_starter.commands.CreateAccountCommand;
import com.secure_auth.axon_starter.commands.DepositMoneyCommand;
import com.secure_auth.axon_starter.events.AccountCreatedEvent;
import com.secure_auth.axon_starter.events.MoneyDepositedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * This aggregate represents a bank account in the Axon framework.
 *
 * Aggregates are a key component in Domain-Driven Design (DDD) and the Axon framework, where they
 * are responsible for encapsulating business rules and ensuring consistency within a particular
 * domain boundary. The @code[BankAccountAggregate] manages the state and behavior of a bank
 * account in the context of event-sourced transactions.
 *
 * This class is annotated with @[Aggregate], making it a recognized aggregate in Axon. It will
 * handle commands and events related to bank account operations, such as creating an account and
 * depositing money.
 *
 * The aggregate listens for two main types of commands:
 *   [CreateAccountCommand]: To create a new bank account.
 *   [DepositMoneyCommand]: To deposit money into an existing account.
 *
 * The state of the bank account is maintained through events:
 *   [AccountCreatedEvent]: Reflects the creation of a bank account.
 *   [MoneyDepositedEvent]: Reflects a deposit made to the account.
 *
 * @see CreateAccountCommand
 * @see DepositMoneyCommand
 * @see AccountCreatedEvent
 * @see MoneyDepositedEvent
 * @see org.axonframework.spring.stereotype.Aggregate
 * @since 1.0
 */

@Aggregate
class BankAccountAggregate {

    /**
     * The unique identifier for the bank account aggregate.
     * This is used by the Axon framework to track and manage this aggregate instance.
     */
    @AggregateIdentifier
    private lateinit var accountId: String;

    /**
     * The current balance of the bank account.
     * Updated through {MoneyDepositedEvent} as deposits are made.
     */
    private var balance: Double = 0.0;

    /**
     * Default constructor required by Axon framework for event sourcing.
     */
    constructor()

    /**
     * Command handler for creating a new bank account.
     * When the {CreateAccountCommand} is received, this handler applies an
     * {AccountCreatedEvent} to initialize the bank account with the provided ID and balance.
     *
     * @param command The command to create a new account, containing the account ID and initial balance.
     */
    @CommandHandler
    constructor(command: CreateAccountCommand) {
        apply { AccountCreatedEvent(command.accountId, command.initialBalance) }
    }

    /**
     * Command handler for depositing money into the bank account.
     * When the {DepositMoneyCommand} is received, this handler applies a
     * {MoneyDepositedEvent} to update the account's balance.
     *
     * @param command The command to deposit money, containing the account ID and deposit amount.
     */
    @CommandHandler
    fun handle(command: DepositMoneyCommand) {
        apply { MoneyDepositedEvent(command.accountId, command.amount) }
    }

    /**
     * Event sourcing handler for the {AccountCreatedEvent}.
     * This method is called to update the aggregate's state when a new account is created. It
     * sets the account ID and initial balance based on the event data.
     *
     * @param event The event reflecting the creation of a bank account.
     */
    @EventSourcingHandler
    fun on(event: AccountCreatedEvent) {
        accountId = event.accountId
        balance = event.initialBalance
    }

    /**
     * Event sourcing handler for the {MoneyDepositedEvent}.
     * This method is called to update the aggregate's balance when money is deposited into the account.
     *
     * @param event The event reflecting a money deposit to the account.
     */
    @EventSourcingHandler
    fun on(event: MoneyDepositedEvent) {
        balance = balance.plus(event.amount)
    }
}
