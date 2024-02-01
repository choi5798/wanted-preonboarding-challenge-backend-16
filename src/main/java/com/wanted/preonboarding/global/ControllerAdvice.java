package com.wanted.preonboarding.global;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFoundExceptionHandle(RuntimeException exception) {
        log.info(MessageFormat.format("Exception occurred: {0}", exception.getClass().getName()));
        log.info(MessageFormat.format("Message: {0}", exception.getMessage()));

        return ErrorResponse.builder(
                exception,
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        ).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse exceptionHandle(Exception exception) {
        log.warn(MessageFormat.format("Exception occurred: {0}", exception.getClass().getName()));
        log.warn(MessageFormat.format("Message: {0}", exception.getMessage()));

        return ErrorResponse.create(
                exception,
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }

}
