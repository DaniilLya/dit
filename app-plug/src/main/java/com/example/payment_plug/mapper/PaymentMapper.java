package com.example.payment_plug.mapper;

import com.example.payment_plug.dto.PaymentRs;
import com.example.payment_plug.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentRs toDto(Payment source);
}