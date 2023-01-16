package com.airscholar.PaymentService.command.api.service;

import com.airscholar.PaymentService.command.api.data.Payment;
import com.airscholar.PaymentService.command.api.data.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment create(Payment payment){
        return paymentRepository.save(payment);
    }

    public Payment findByTransactionId(String transactionId){
        return paymentRepository.findByTransactionId(transactionId);
    }

    public Payment findById(Long id){
        return paymentRepository.findById(id).get();
    }
}
