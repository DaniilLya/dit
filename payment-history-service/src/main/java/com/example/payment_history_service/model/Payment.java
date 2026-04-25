package com.example.payment_history_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("payment_history")
public class Payment implements Persistable<UUID> {
    @Id
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

    @Transient
    private boolean newEntity = true;

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void markNotNew() {
        this.newEntity = false;
    }
}
