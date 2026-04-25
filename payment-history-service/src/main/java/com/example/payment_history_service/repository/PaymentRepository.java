package com.example.payment_history_service.repository;

import com.example.payment_history_service.model.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface PaymentRepository extends ReactiveCrudRepository<Payment, UUID> {
    Flux<Payment> findTop10ByOrderByCreatedAtDesc();
}
