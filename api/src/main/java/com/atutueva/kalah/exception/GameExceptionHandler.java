package com.atutueva.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameGeneralException.class)
    public final ResponseEntity handleGameException(GameGeneralException exception) {
        ExceptionResponse response = ExceptionResponse.of(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public final ResponseEntity handleGameNotFoundException(GameNotFoundException exception) {
        ExceptionResponse response = ExceptionResponse.of(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
