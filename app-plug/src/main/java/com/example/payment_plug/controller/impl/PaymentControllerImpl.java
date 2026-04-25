package com.example.payment_plug.controller.impl;

import com.example.payment_plug.controller.PaymentController;
import com.example.payment_plug.dto.PaymentRs;
import com.example.payment_plug.mapper.PaymentMapper;
import com.example.payment_plug.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentRs getPayment() {
        return paymentMapper.toDto(paymentService.create());
    }
}
