package com.example.payment_history_service.controller.impl;

import com.example.payment_history_service.controller.PaymentController;
import com.example.payment_history_service.dto.PaymentDto;
import com.example.payment_history_service.mapper.PaymentMapper;
import com.example.payment_history_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @Override
    public Mono<PaymentDto> importPayment() {
        return paymentService.importPayment()
                .map(paymentMapper::toDto);
    }

    @Override
    public Flux<PaymentDto> getLatestPayments() {
        return paymentService.getLatestPayments()
                .map(paymentMapper::toDto);
    }
}
