package com.airscholar.PaymentService.service;

import com.airscholar.CommonService.data.Payment;
import com.airscholar.PaymentService.data.PaymentRepository;
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
}
