package com.example.payment_history_service.controller;

import com.example.payment_history_service.dto.ErrorRs;
import com.example.payment_history_service.dto.PaymentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.payment_history_service.consts.EndpointConstants.PAYMENT_BASE_ENDPOINT;
import static com.example.payment_history_service.consts.HttpConstants.BAD_REQUEST;
import static com.example.payment_history_service.consts.HttpConstants.OK;
import static com.example.payment_history_service.consts.HttpConstants.SERVER_ERROR;

@RequestMapping(PAYMENT_BASE_ENDPOINT)
public interface PaymentController {

    @Operation(summary = "Запросить платёж из app-plug и сохранить его в историю")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "Платёж успешно сохранён",
                    content = @Content(schema = @Schema(implementation = PaymentDto.class))
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
            )
    })
    @PostMapping
    Mono<PaymentDto> importPayment();

    @Operation(summary = "Получить последние 10 платежей из истории")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "История платежей успешно получена",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDto.class)))
            ),
            @ApiResponse(
                    responseCode = SERVER_ERROR,
                    description = "Ошибка на стороне сервера",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))
            )
    })
    @GetMapping
    Flux<PaymentDto> getLatestPayments();
}
