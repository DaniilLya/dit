package com.example.payment_history_service.service;

import com.example.payment_history_service.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Mono<Payment> importPayment();

    Flux<Payment> getLatestPayments();
}
