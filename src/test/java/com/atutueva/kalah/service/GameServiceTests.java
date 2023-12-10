package com.atutueva.kalah.service;

import com.atutueva.kalah.dto.GameResponse;
import com.atutueva.kalah.exception.GameException;
import com.atutueva.kalah.model.GameState;
import com.atutueva.kalah.model.GameStateBuilder;
import com.atutueva.kalah.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class GameServiceTests {

    @Autowired
    private GameService gameService;

    @Test
    public void createGame() {
        GameResponse newGame = gameService.createGame();

        GameState expectedInitState = new GameStateBuilder(6)
                .player1Pits(6, 6, 6, 6, 6, 6)
                .player1Kalah(0)
                .player2Pits(6, 6, 6, 6, 6, 6)
                .player2Kalah(0)
                .status(GameStatus.PlAYER1_TURN);

        GameResponse expected = new GameResponse(newGame.getId(), expectedInitState);

        assertEquals(gameService.getAllGames().size(), 1);
        assertEquals(newGame, expected);
    }

    @Test
    public void makeMoveWithInvalidGameId() {
        assertThrows(GameException.class, () -> {
            gameService.makeMove(UUID.randomUUID(), 4);
        });
    }
}
