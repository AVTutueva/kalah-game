package com.atutueva.kalah.exception;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(UUID id) {
        super("Game with id=" + id + " Not Found");
    }
}
