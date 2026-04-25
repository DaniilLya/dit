package com.example.payment_history_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient appPlugWebClient(
            WebClient.Builder webClientBuilder,
            @Value("${clients.app-plug.base-url}") String appPlugBaseUrl
    ) {
        return webClientBuilder.baseUrl(appPlugBaseUrl).build();
    }
}
