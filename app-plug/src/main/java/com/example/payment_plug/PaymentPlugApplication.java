package com.example.payment_plug;

import com.example.payment_plug.config.RandomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RandomConfig.class)
@SpringBootApplication
public class PaymentPlugApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentPlugApplication.class, args);
    }

}
