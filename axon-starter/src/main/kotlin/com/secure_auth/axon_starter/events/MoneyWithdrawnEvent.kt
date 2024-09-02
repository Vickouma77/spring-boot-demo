package com.secure_auth.axon_starter.events

data class MoneyWithdrawnEvent(
    val accountId: String,
    val amountToWithdraw: Double
)