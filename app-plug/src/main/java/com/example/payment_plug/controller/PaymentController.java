package com.example.payment_plug.controller;

import com.example.payment_plug.dto.ErrorRs;
import com.example.payment_plug.dto.PaymentRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.payment_plug.consts.EndpointConstants.PAYMENT_BASE_ENDPOINT;
import static com.example.payment_plug.consts.HttpConstants.BAD_REQUEST;
import static com.example.payment_plug.consts.HttpConstants.OK;
import static com.example.payment_plug.consts.HttpConstants.SERVER_ERROR;

@RequestMapping(PAYMENT_BASE_ENDPOINT)
public interface PaymentController {
    @Operation(summary = "Получить платёж с задержкой 200ms")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "Платёж успешно сгенерирован",
                    content = @Content(schema = @Schema(implementation = PaymentRs.class))
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "Некорректный запрос",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))
            ),
            @ApiResponse(
                    responseCode = SERVER_ERROR,
                    description = "Ошибка на стороне сервера",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))
            ),
    }
    )
    @GetMapping
    PaymentRs getPayment();
}