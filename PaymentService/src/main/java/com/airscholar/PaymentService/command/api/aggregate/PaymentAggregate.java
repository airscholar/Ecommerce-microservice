package com.airscholar.PaymentService.command.api.aggregate;

import com.airscholar.CommonService.command.CompletePaymentCommand;
import com.airscholar.PaymentService.command.api.command.CreatePaymentCommand;
import com.airscholar.CommonService.command.ValidatePaymentCommand;
import com.airscholar.CommonService.event.PaymentCompletedEvent;
import com.airscholar.CommonService.event.PaymentCreatedEvent;
import com.airscholar.CommonService.event.PaymentValidatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Aggregate
@Slf4j
@NoArgsConstructor
public class PaymentAggregate {
    @AggregateIdentifier
    private String transactionId;
    private String orderId;
    private String paymentStatus;

    @CommandHandler
    public PaymentAggregate(CreatePaymentCommand createPaymentCommand){
        log.info("Handling CreatePaymentCommand {}", createPaymentCommand);
        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();

        BeanUtils.copyProperties(createPaymentCommand, paymentCreatedEvent);

        AggregateLifecycle.apply(paymentCreatedEvent);
    }

    @CommandHandler
    public void handle(ValidatePaymentCommand validatePaymentCommand){
        PaymentValidatedEvent paymentValidatedEvent = PaymentValidatedEvent.builder()
                .orderId(validatePaymentCommand.getOrderId())
                .transactionId(validatePaymentCommand.getTransactionId())
                .paymentStatus(validatePaymentCommand.getPaymentStatus())
                .build();

        AggregateLifecycle.apply(paymentValidatedEvent);

    }
    @CommandHandler
    public void handle(CompletePaymentCommand completePaymentCommand){
        PaymentCompletedEvent paymentCompletedEvent = PaymentCompletedEvent.builder()
                .orderId(completePaymentCommand.getOrderId())
                .transactionId(completePaymentCommand.getTransactionId())
                .paymentStatus(completePaymentCommand.getPaymentStatus())
                .build();

        AggregateLifecycle.apply(paymentCompletedEvent);

    }

    @EventSourcingHandler
    public void on(PaymentCreatedEvent event){
        this.transactionId = event.getTransactionId();
        this.orderId = event.getOrderId();
        this.paymentStatus = event.getPaymentStatus();
    }
    @EventSourcingHandler
    public void on(PaymentValidatedEvent event){
        this.transactionId = event.getTransactionId();
        this.orderId = event.getOrderId();
        this.paymentStatus = event.getPaymentStatus();
    }

}
