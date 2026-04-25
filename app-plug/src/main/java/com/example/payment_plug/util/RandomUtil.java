package com.example.payment_plug.util;

import com.example.payment_plug.config.RandomConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class RandomUtil {

    private final RandomConfig randomConfig;

    public BigDecimal randomBigDecimal() {
        double random = ThreadLocalRandom.current().nextDouble(
                randomConfig.minBigDecimalValue(),
                randomConfig.maxBigDecimalValue()
        );

        return BigDecimal.valueOf(random)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
