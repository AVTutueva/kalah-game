package com.atutueva.kalah.controller;

import com.atutueva.kalah.dto.GameResponse;
import com.atutueva.kalah.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame() {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame());
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<GameResponse> makeMove(@PathVariable UUID gameId, @RequestParam int pitIndex) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.makeMove(gameId, pitIndex));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getById(@PathVariable UUID gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getById(gameId));
    }
}
