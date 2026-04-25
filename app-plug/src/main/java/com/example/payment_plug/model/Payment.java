package com.example.payment_plug.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private UUID id;
    private UUID externalId;
    private BigDecimal amount;
    private String currencyCode;
    private BigDecimal currencyRate;
    private int currencyUnits;
    private String statusCode;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
