package com.example.payment_plug.exception;

import com.example.payment_plug.dto.ErrorRs;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public ErrorRs handleException(Exception ex){
        return new ErrorRs(ex.getMessage());
    }
}