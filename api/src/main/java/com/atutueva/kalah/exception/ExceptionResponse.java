package com.atutueva.kalah.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private final String message;
    private final LocalDateTime timestamp;
    private final HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ExceptionResponse of(final String message, HttpStatus status) {
        return new ExceptionResponse(message, status);
    }
}
