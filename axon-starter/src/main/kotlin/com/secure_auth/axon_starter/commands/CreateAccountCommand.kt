package com.secure_auth.axon_starter.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateAccountCommand(
    @TargetAggregateIdentifier val accountId: String,
    val initialBalance: Double
)