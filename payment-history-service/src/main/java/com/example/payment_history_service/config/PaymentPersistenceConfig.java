package com.example.payment_history_service.config;

import com.example.payment_history_service.model.Payment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentPersistenceConfig {

    @Bean
    public AfterConvertCallback<Payment> paymentAfterConvertCallback() {
        return (payment, table) -> {
            payment.markNotNew();
            return Mono.just(payment);
        };
    }

    @Bean
    public AfterSaveCallback<Payment> paymentAfterSaveCallback() {
        return (payment, outboundRow, table) -> {
            payment.markNotNew();
            return Mono.just(payment);
        };
    }
}
