package com.secure_auth.axon_starter.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class WithdrawMoneyCommand(
    @TargetAggregateIdentifier val accountId: String,
    val amountToWithdraw: Double
)
