package com.example.payment_history_service.client.impl;

import com.example.payment_history_service.client.AppPlugClient;
import com.example.payment_history_service.client.dto.AppPlugPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AppPlugClientImpl implements AppPlugClient {

    private final WebClient appPlugWebClient;

    @Override
    public Mono<AppPlugPaymentDto> getPayment() {
        return appPlugWebClient.get()
                .uri("/api/v1/payment")
                .retrieve()
                .bodyToMono(AppPlugPaymentDto.class);
    }
}
