package com.atutueva.kalah.service;

import com.atutueva.kalah.dto.GameResponse;
import com.atutueva.kalah.exception.GameNotFoundException;
import com.atutueva.kalah.model.Game;
import com.atutueva.kalah.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameResponse getById(UUID id) {
        Game game = gameRepository.getById(id);
        if (game == null) {
            throw new GameNotFoundException(id);
        }
        return new GameResponse(id, game.getState());
    }

    public GameResponse createGame() {
        Game game = Game.newGame();
        UUID gameId = gameRepository.save(game);
        return new GameResponse(gameId, game.getState());
    }

    public GameResponse makeMove(UUID id, int pitIndex) {
        Game game = gameRepository.getById(id);
        if (game == null) {
            throw new GameNotFoundException(id);
        }
        game.makeMove(pitIndex);
        return new GameResponse(id, game.getState());
    }
}
