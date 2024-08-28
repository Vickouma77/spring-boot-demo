package com.secure_auth.axon_starter.api

import com.secure_auth.axon_starter.commands.CreateAccountCommand
import com.secure_auth.axon_starter.commands.DepositMoneyCommand
import com.secure_auth.axon_starter.query.AccountQuery
import com.secure_auth.axon_starter.query.model.BankAccountProjection
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * Controller for handling bank account operations such as creating accounts, depositing money,
 * and retrieving account information.
 *
 * This controller uses Axon Framework's [CommandGateway] for dispatching commands
 * and [QueryGateway] for handling queries. The operations supported include:
 *
 *   Creating a new bank account
 *   Depositing money into an existing account
 *   Retrieving account information
 *
 * @see CommandGateway
 * @see QueryGateway
 * @since 1.0
 */

@RestController
@RequestMapping("/bank-account")
class BankAccountController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway
) {
    /**
     * Creates a new bank account with the specified initial balance.
     *
     * A new account is created by generating a unique account ID and sending a
     * {CreateAccountCommand} through the command gateway.
     *
     * @param request the request containing the initial balance for the new account
     * @see CreateAccountCommand
     */
    @PostMapping
    fun createAccount(@RequestParam request: CreateAccountRequest) {
        val command = CreateAccountCommand(UUID.randomUUID().toString(), request.initialBalance)
        commandGateway.sendAndWait<Void>(command)
    }

    /**
     * Deposits money into an existing bank account.
     *
     * This method handles the deposit operation by sending a {DepositMoneyCommand}
     * with the specified account ID and deposit amount.
     *
     * @param accountId the ID of the account to deposit money into
     * @param request the request containing the deposit amount
     * @see DepositMoneyCommand
     */
    @PostMapping("/{accountId}/deposit")
    fun depositMoney(@PathVariable accountId: String, @RequestBody request: DepositMoneyRequest) {
        val command = DepositMoneyCommand(accountId, request.amount)
        commandGateway.sendAndWait<Void>(command)
    }

    /**
     * Retrieves the details of a bank account.
     *
     * The account details are fetched by sending an {@link AccountQuery} and
     * returning the {BankAccountProjection} result.
     *
     * @param accountId the ID of the account to retrieve
     * @return the projection of the bank account details
     * @see AccountQuery
     * @see BankAccountProjection
     */
    @GetMapping("/{accountId}")
    fun getAccount(@PathVariable accountId: String): BankAccountProjection {
        return queryGateway.query(AccountQuery(accountId), BankAccountProjection::class.java).join()
    }
}
/**
 * Request objects for creating a bank account and making deposits.
 *
 * These classes hold the initial balance to be used when creating a new account and
 * the amount deposited.
 *
 */
data class CreateAccountRequest(val initialBalance: Double)
data class DepositMoneyRequest(val amount: Double)