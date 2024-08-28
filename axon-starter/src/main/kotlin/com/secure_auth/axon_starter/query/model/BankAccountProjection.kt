package com.secure_auth.axon_starter.query.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
/**
 * Projection model representing a bank account in a query-optimized format.
 *
 * The [BankAccountProjection] class is an entity used to store and retrieve bank account data
 * in a query model. It represents the state of a bank account, including the account ID and current balance.
 *
 * This class is annotated with [Entity], making it a JPA entity that can be persisted in the database.
 * The {@code accountId} is marked as the [Id], serving as the primary key for the entity.
 *
 * @param accountId The unique identifier of the bank account.
 * @param balance The current balance of the bank account.
 *
 * @see com.secure_auth.axon_starter.processor.BankAccountProcessor
 * @see com.secure_auth.axon_starter.query.repository.BankAccountRepository
 * @since 1.0
 */

@Entity
data class BankAccountProjection(

    @Id val accountId: String,
    var balance: Double
)