package com.example.payment_history_service.client;

import com.example.payment_history_service.client.dto.AppPlugPaymentDto;
import reactor.core.publisher.Mono;

public interface AppPlugClient {
    Mono<AppPlugPaymentDto> getPayment();
}
