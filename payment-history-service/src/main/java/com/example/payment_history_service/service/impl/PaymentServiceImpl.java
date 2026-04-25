package com.example.payment_history_service.service.impl;

import com.example.payment_history_service.model.Payment;
import com.example.payment_history_service.repository.PaymentRepository;
import com.example.payment_history_service.service.AppPlugService;
import com.example.payment_history_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AppPlugService appPlugService;
    private final PaymentRepository paymentRepository;

    @Override
    public Mono<Payment> importPayment() {
        return appPlugService.fetchPayment()
                .flatMap(paymentRepository::save);
    }

    @Override
    public Flux<Payment> getLatestPayments() {
        return paymentRepository.findTop10ByOrderByCreatedAtDesc();
    }
}
