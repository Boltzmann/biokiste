package com.github.boltzmann.biokiste.backend.controller.status;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityEncoderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request){

        ErrorResponse bodyOfResponse = ErrorResponse.builder()
                .errorMessage("There was an illegal argument in this request.")
                .timestamp(LocalDateTime.now())
                .requestUri(request.getDescription(false))
                .build();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
