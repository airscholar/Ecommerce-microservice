package com.airscholar.OrderService.saga;

import com.airscholar.CommonService.command.CompletePaymentCommand;
import com.airscholar.CommonService.command.CreatePaymentCommand;
import com.airscholar.CommonService.command.ValidatePaymentCommand;
import com.airscholar.CommonService.event.PaymentCreatedEvent;
import com.airscholar.CommonService.event.PaymentValidatedEvent;
import com.airscholar.CommonService.queries.GetCustomerByCustomerIdQuery;
import com.airscholar.CommonService.queries.GetPaymentByTransactionId;
import com.airscholar.CustomerService.command.api.entity.Customer;
import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.event.OrderCreatedEvent;
import com.airscholar.OrderService.query.api.query.GetOrderByIdQuery;
import com.airscholar.PaymentService.data.Payment;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;

import java.util.UUID;

@Saga
@Slf4j
public class OrderSagaProcessing {

    private transient CommandGateway commandGateway;
    private transient QueryGateway queryGateway;

    public OrderSagaProcessing(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event){

        SagaLifecycle.associateWith("transactionId", "orderId");

        CreatePaymentCommand paymentCreatedCommand = CreatePaymentCommand.builder()
                .transactionId(UUID.randomUUID().toString())
                .orderId(event.getOrderId())
                .orderStatus("CREATED")
                .build();

        commandGateway.sendAndWait(paymentCreatedCommand);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void handle(PaymentCreatedEvent event){
        //validate payment here
        //get customer information
        GetOrderByIdQuery getOrderByIdQuery = new GetOrderByIdQuery(event.getOrderId());
        Order order = queryGateway.query(getOrderByIdQuery, ResponseTypes.instanceOf(Order.class)).join();

        if(order == null){
            //start the compensating transaction
            return;
        }

        //use customer payment information to validate payment
        GetCustomerByCustomerIdQuery getCustomerByCustomerIdQuery =
                new GetCustomerByCustomerIdQuery(order.getCustomerId());
        Customer customer = queryGateway.query(getCustomerByCustomerIdQuery,
                ResponseTypes.instanceOf(Customer.class)).join();

        if(customer == null){
            //start the compensating transaction
            return;
        }

        if(customer.getCardDetails() != null) {
            try {
                //charge customer card and
                log.info("card charge sucessful");

            } catch (Exception ex) {
                log.error("Cant charge customer card");
                //start the compensating transaction
                return;
            }

            ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                    .orderId(event.getOrderId())
                    .transactionId(event.getTransactionId())
                    .paymentStatus("PAYMENT_VALIDATED")
                    .build();
            commandGateway.sendAndWait(validatePaymentCommand);
        } else {
            log.error("Card not exist for customer");
        }
    }

    @SagaEventHandler(associationProperty = "transactionId")
    @EndSaga
    public void handle(PaymentValidatedEvent event){
        //check for payment validity
        GetPaymentByTransactionId getPaymentByTransactionId = new GetPaymentByTransactionId(event.getTransactionId());
        Payment payment = queryGateway.query(getPaymentByTransactionId, ResponseTypes.instanceOf(Payment.class)).join();
        if(payment.getPaymentStatus() != "PAYMENT_VALIDATED"){
            log.error("Customer Payment not validated.");
            return;
        }

        //validate payment event
        CompletePaymentCommand completePaymentCommand = CompletePaymentCommand.builder()
                .orderId(event.getOrderId())
                .transactionId(event.getTransactionId())
                .paymentStatus("COMPLETED")
                .build();

        commandGateway.sendAndWait(completePaymentCommand);
    }
}
