package com.example.payment_history_service.service;

import com.example.payment_history_service.model.Payment;
import reactor.core.publisher.Mono;

public interface AppPlugService {
    Mono<Payment> fetchPayment();
}
