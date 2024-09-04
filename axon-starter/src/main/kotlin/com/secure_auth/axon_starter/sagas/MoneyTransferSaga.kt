package com.secure_auth.axon_starter.sagas

import com.secure_auth.axon_starter.commands.CreditAccountCommand
import com.secure_auth.axon_starter.commands.DebitAccountCommand
import com.secure_auth.axon_starter.events.CreditedAccountEvent
import com.secure_auth.axon_starter.events.InitiatedTransferEvent
import com.secure_auth.axon_starter.events.MoneyDebitEvent
import com.secure_auth.axon_starter.events.TransactionFailedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired


@Saga
class MoneyTransferSaga {

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    fun handle(event: InitiatedTransferEvent) {
        val debitCommand = DebitAccountCommand(
            accountId = event.targetAccountId,
            transactionId = event.transactionId,
            amount = event.amount
        )
        commandGateway.send<Any>(debitCommand)
    }

    @SagaEventHandler(associationProperty = "transactionId")
    fun on(event: MoneyDebitEvent) {
        val creditCommand = CreditAccountCommand(
            accountId = event.accountId,
            transactionId = event.transactionId,
            amount =  event.amount
        )
        commandGateway.send<Any>(creditCommand)
    }

    @SagaEventHandler(associationProperty = "transactionId")
    fun on(event: CreditedAccountEvent){
        SagaLifecycle.end()
    }

    @SagaEventHandler(associationProperty = "transactionId")
    fun on(event: TransactionFailedEvent) {
        SagaLifecycle.end()
    }
}