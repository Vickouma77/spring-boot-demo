package com.secure_auth.axon_starter.query.model.handler

import com.secure_auth.axon_starter.query.AccountQuery
import com.secure_auth.axon_starter.query.model.BankAccountProjection
import com.secure_auth.axon_starter.query.repository.BankAccountRepository
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component
import java.util.*
/**
 * Query handler responsible for handling account-related queries.
 *
 * The [BankAccountQueryHandler] listens for [AccountQuery] requests and responds
 * with the corresponding {BankAccountProjection}, if found in the repository.
 *
 * This handler interacts with the [BankAccountRepository] to fetch data based on the
 * account ID provided in the query.
 *
 * The {QueryHandler} annotation indicates that this method will handle query messages
 * of type {AccountQuery} on the Axon query bus.
 *
 * @see AccountQuery
 * @see BankAccountProjection
 * @see BankAccountRepository
 * @since 1.0
 */
@Component
class BankAccountQueryHandler(
    private val repository: BankAccountRepository
) {

    /**
     * Handles {AccountQuery} and returns the corresponding {BankAccountProjection}.
     *
     * This method looks up the bank account in the repository using the account ID provided
     * in the query. If found, it returns an {@link Optional} containing the projection.
     *
     *
     * @param query The query containing the account ID to be fetched.
     * @return An {@link Optional} of {@link BankAccountProjection}, which will be empty if no account is found.
     */
    @QueryHandler
    fun handle(query: AccountQuery): Optional<BankAccountProjection> {
        return repository.findById(query.accountId)
    }
}