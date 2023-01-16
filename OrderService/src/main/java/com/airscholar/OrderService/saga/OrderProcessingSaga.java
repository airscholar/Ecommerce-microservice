package com.airscholar.OrderService.saga;

import com.airscholar.CommonService.command.CompletePaymentCommand;
import com.airscholar.PaymentService.command.api.command.CreatePaymentCommand;
import com.airscholar.CommonService.command.ValidatePaymentCommand;
import com.airscholar.CommonService.event.PaymentCreatedEvent;
import com.airscholar.CommonService.event.PaymentValidatedEvent;
import com.airscholar.CustomerService.query.api.queries.GetCustomerByCustomerIdQuery;
import com.airscholar.PaymentService.query.api.query.GetPaymentByTransactionIdQuery;
import com.airscholar.CustomerService.command.api.entity.Customer;
import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.event.OrderCreatedEvent;
import com.airscholar.OrderService.query.api.query.GetOrderByIdQuery;
import com.airscholar.PaymentService.command.api.data.Payment;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
@NoArgsConstructor
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("Handling OrderCreatedEvent {}", event);

        String transactionId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("transactionId", transactionId);

        CreatePaymentCommand createPaymentCommand = CreatePaymentCommand.builder()
                .orderId(event.getOrderId())
                .transactionId(transactionId)
                .paymentStatus("CREATED")
                .build();
        log.info("Hit this point, {}", createPaymentCommand);

        commandGateway.sendAndWait(createPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void handle(PaymentCreatedEvent event){
        log.info("handling PaymentCreatedEvent {}", event);
        //validate payment here
        //get customer information
        GetOrderByIdQuery getOrderByIdQuery = new GetOrderByIdQuery(event.getOrderId());
        Order order = null;

        try{
            order = queryGateway.query(getOrderByIdQuery, ResponseTypes.instanceOf(Order.class)).join();
        }catch(Exception ex){
            log.error("Error occured while getting order {}", ex);
            //start the compensating transaction
        }

        //use customer payment information to validate payment
        GetCustomerByCustomerIdQuery getCustomerByCustomerIdQuery =
                new GetCustomerByCustomerIdQuery(order.getCustomerId());
        Customer customer = null;

        try{
            customer = queryGateway.query(getCustomerByCustomerIdQuery,
                    ResponseTypes.instanceOf(Customer.class)).join();
            log.info("Customer {}", customer);
        }catch(Exception ex){
            log.error("Error occured while getting customer {}", ex);
            //start the compensating transaction
        }

        if(customer.getCardDetails() != null) {
            try {
                //charge customer card and
                log.info("card charge sucessful");

                ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                        .orderId(event.getOrderId())
                        .transactionId(event.getTransactionId())
                        .paymentStatus("PAYMENT_VALIDATED")
                        .build();
                commandGateway.sendAndWait(validatePaymentCommand);
            } catch (Exception ex) {
                log.error("Cant charge customer card, {}", ex);
                //start the compensating transaction
                return;
            }

        } else {
            log.error("Card not exist for customer");
        }
    }

    @SagaEventHandler(associationProperty = "transactionId")
    @EndSaga
    public void handle(PaymentValidatedEvent event){
        log.info("Handling PaymentValidatedEvent {}", event);
        //check for payment validity
        GetPaymentByTransactionIdQuery getPaymentByTransactionIdQuery = new GetPaymentByTransactionIdQuery(event.getTransactionId());
        Payment payment = queryGateway.query(getPaymentByTransactionIdQuery, ResponseTypes.instanceOf(Payment.class)).join();
        if(payment.getPaymentStatus() == "PAYMENT_VALIDATED"){
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
