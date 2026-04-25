package com.example.payment_history_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Платёж")
public record PaymentDto(
        @Schema(description = "Внутренний уникальный идентификатор записи платежа")
        UUID id,
        @Schema(description = "Внешний бизнес-идентификатор для взаимодействия между сервисами")
        UUID externalId,
        @Schema(description = "Сумма платежа")
        BigDecimal amount,
        @Schema(description = "Код валюты")
        String currencyCode,
        @Schema(description = "Курс валюты")
        BigDecimal currencyRate,
        @Schema(description = "Количество единиц валюты")
        int currencyUnits,
        @Schema(description = "Код статуса платежа")
        String statusCode,
        @Schema(description = "Комментарий")
        String description,
        @Schema(description = "Дата создания записи платежа")
        LocalDateTime createdAt,
        @Schema(description = "Дата последнего обновления записи платежа")
        LocalDateTime updatedAt
) {
}
