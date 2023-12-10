package com.atutueva.kalah.service;

import com.atutueva.kalah.dto.GameResponse;
import com.atutueva.kalah.exception.GameException;
import com.atutueva.kalah.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {
    private final Map<UUID, Game> games = new HashMap<>();

    public GameResponse createGame() {
        Game game = Game.newGame();

        UUID gameId = UUID.randomUUID();
        games.put(gameId, game);

        return new GameResponse(gameId, game.getState());
    }

    public GameResponse makeMove(UUID gameId, int pitIndex) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new GameException("Game wit id=" + gameId + " Not Found");
        }

        game.makeMove(pitIndex);
        return new GameResponse(gameId, game.getState());
    }

    public Map<UUID, GameResponse> getAllGames() {
        Map<UUID, GameResponse> copyOfGames = new HashMap<>();

        for (Map.Entry<UUID, Game> entry : games.entrySet()) {
            UUID gameId = entry.getKey();
            Game game = entry.getValue();
            GameResponse gameResponse = new GameResponse(gameId, game.getState());
            copyOfGames.put(gameId, gameResponse);
        }

        return copyOfGames;
    }
}
