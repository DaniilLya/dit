package com.example.payment_history_service.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppPlugPaymentDto(
        UUID id,
        UUID externalId,
        BigDecimal amount,
        String currencyCode,
        BigDecimal currencyRate,
        int currencyUnits,
        String statusCode,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
