package com.atutueva.kalah.repository;

import com.atutueva.kalah.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {
    private final Map<UUID, Game> games = new ConcurrentHashMap<>();

    public UUID save(Game game) {
        UUID gameId = UUID.randomUUID();
        games.put(gameId, game);

        return gameId;
    }

    public Game getById(UUID id) {
        Game game = games.get(id);
        return game;
    }
}
