package com.example.payment_history_service.service.impl;

import com.example.payment_history_service.client.AppPlugClient;
import com.example.payment_history_service.mapper.PaymentMapper;
import com.example.payment_history_service.model.Payment;
import com.example.payment_history_service.service.AppPlugService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AppPlugServiceImpl implements AppPlugService {

    private final AppPlugClient appPlugClient;
    private final PaymentMapper paymentMapper;

    @Override
    public Mono<Payment> fetchPayment() {
        return appPlugClient.getPayment()
                .map(paymentMapper::toEntity);
    }
}
