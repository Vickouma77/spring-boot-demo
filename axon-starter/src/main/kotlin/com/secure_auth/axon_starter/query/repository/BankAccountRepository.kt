package com.secure_auth.axon_starter.query.repository

import com.secure_auth.axon_starter.query.model.BankAccountProjection
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository: JpaRepository<BankAccountProjection, String>
