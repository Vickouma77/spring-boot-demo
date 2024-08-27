package com.secure_auth.axon_starter.query.model

import jakarta.persistence.Entity
import jakarta.persistence.Id


@Entity
data class BankAccountProjection(

    @Id val accountId: String,
    val balance: Double
)