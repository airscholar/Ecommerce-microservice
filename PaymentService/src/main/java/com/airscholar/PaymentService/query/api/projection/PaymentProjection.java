package com.airscholar.PaymentService.query.api.projection;

import com.airscholar.PaymentService.command.api.data.Payment;
import com.airscholar.PaymentService.command.api.service.PaymentService;
import com.airscholar.PaymentService.query.api.query.GetPaymentByIdQuery;
import com.airscholar.PaymentService.query.api.query.GetPaymentByTransactionIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class PaymentProjection {
    private PaymentService paymentService;

    public PaymentProjection(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @QueryHandler
    public Payment handle(GetPaymentByIdQuery getPaymentByIdQuery){
        return paymentService.findById(getPaymentByIdQuery.getId());
    }

    @QueryHandler
    public Payment handle(GetPaymentByTransactionIdQuery getPaymentByTransactionIdQuery){
        return paymentService.findByTransactionId(getPaymentByTransactionIdQuery.getTransactionId());
    }
}
