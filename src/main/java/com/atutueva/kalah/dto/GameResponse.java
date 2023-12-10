package com.atutueva.kalah.dto;

import com.atutueva.kalah.model.GameState;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;
import java.util.UUID;

@Schema
public class GameResponse {
    private UUID id;
    private GameState state;

    public GameResponse(UUID id, GameState state) {
        this.id = id;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public GameState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameResponse response = (GameResponse) o;
        return Objects.equals(id, response.id) && Objects.equals(state, response.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state);
    }

    @Override
    public String toString() {
        return "GameResponse{" +
                "id=" + id +
                ", state=" + state +
                '}';
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
