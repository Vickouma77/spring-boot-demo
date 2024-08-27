package com.secure_auth.axon_starter.query.model.handler

import com.secure_auth.axon_starter.query.AccountQuery
import com.secure_auth.axon_starter.query.model.BankAccountProjection
import com.secure_auth.axon_starter.query.repository.BankAccountRepository
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class BankAccountQueryHandler(
    private val repository: BankAccountRepository
) {
    @QueryHandler
    fun handle(query: AccountQuery): BankAccountProjection? {
        return repository.findById(query.accountId)
    }
}