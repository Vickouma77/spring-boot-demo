package com.secure_auth.axon_starter.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class DepositMoneyCommand(
    @TargetAggregateIdentifier val accountId: String,
    val amount: Double
)
