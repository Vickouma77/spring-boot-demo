package com.secure_auth.axon_starter.query.repository

import com.secure_auth.axon_starter.query.model.BankAccountProjection

interface BankAccountRepository {
    fun findById(accountId: String): BankAccountProjection
    //TODO
}