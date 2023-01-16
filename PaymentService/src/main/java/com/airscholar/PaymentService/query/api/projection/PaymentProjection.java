package com.airscholar.PaymentService.query.api.projection;

import com.airscholar.PaymentService.data.Payment;
import com.airscholar.PaymentService.query.api.query.GetPaymentByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class PaymentProjection {
    private QueryGateway queryGateway;

    public PaymentProjection(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @QueryHandler
    public Payment handle(GetPaymentByIdQuery getPaymentByIdQuery){
        return queryGateway.query(getPaymentByIdQuery, ResponseTypes.instanceOf(Payment.class)).join();
    }
}
