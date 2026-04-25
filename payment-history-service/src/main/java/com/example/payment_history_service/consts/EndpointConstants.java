package com.example.payment_history_service.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointConstants {
    public static final String API = "api/v1";
    public static final String PAYMENT_BASE_ENDPOINT = API + "/payment";
}
