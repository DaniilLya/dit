package com.example.payment_plug.service.impl;

import com.example.payment_plug.model.Payment;
import com.example.payment_plug.service.PaymentService;
import com.example.payment_plug.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RandomUtil randomUtil;

    @Override
    public Payment create() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException("Ошибка при попытке сделать искусственную задержку");
        }
        return new Payment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                randomUtil.randomBigDecimal(),
                "610",
                randomUtil.randomBigDecimal(),
                1,
                "100",
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
                );
    }
}
