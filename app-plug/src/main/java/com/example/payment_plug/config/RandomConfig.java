package com.example.payment_plug.config;

import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "random-generator")
public record RandomConfig(
        @Positive double minBigDecimalValue,
        @Positive double maxBigDecimalValue
) {
}
