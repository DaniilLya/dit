package com.example.payment_history_service.mapper;

import com.example.payment_history_service.client.dto.AppPlugPaymentDto;
import com.example.payment_history_service.dto.PaymentDto;
import com.example.payment_history_service.model.Payment;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "newEntity", ignore = true)
    Payment toEntity(AppPlugPaymentDto source);

    PaymentDto toDto(Payment source);
}
