package com.airscholar.PaymentService.command.api.event;

import com.airscholar.CommonService.event.PaymentCompletedEvent;
import com.airscholar.CommonService.event.PaymentCreatedEvent;
import com.airscholar.CommonService.event.PaymentValidatedEvent;
import com.airscholar.PaymentService.command.api.data.Payment;
import com.airscholar.PaymentService.command.api.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventsHandler {

    private PaymentService paymentService;
    public PaymentEventsHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @EventHandler
    public void handle(PaymentCreatedEvent event){
        log.info("Handling PaymentCreatedEvent, {}", event);
        Payment payment = new Payment();
        BeanUtils.copyProperties(event, payment);

        paymentService.create(payment);
    }

    @EventHandler
    public void handle(PaymentValidatedEvent event){
        log.info("Handling PaymentValidatedEvent, {}", event);
        Payment payment = this.paymentService.findByTransactionId(event.getTransactionId());

        payment.setPaymentStatus(event.getPaymentStatus());

        paymentService.create(payment);
    }

    @EventHandler
    public void handle(PaymentCompletedEvent event){
        log.info("Handling PaymentCompletedEvent, {}", event);
        Payment payment = this.paymentService.findByTransactionId(event.getTransactionId());

        payment.setPaymentStatus(event.getPaymentStatus());

        paymentService.create(payment);
    }

}
