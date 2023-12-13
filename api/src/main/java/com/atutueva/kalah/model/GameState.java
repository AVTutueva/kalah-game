package com.atutueva.kalah.model;

import java.util.Objects;

public class GameState {
    private GameStatus status;
    private PlayerBoard player1Board;
    private PlayerBoard player2Board;

    public GameStatus getStatus() {
        return status;
    }

    public PlayerBoard getPlayer1Board() {
        return player1Board;
    }

    public PlayerBoard getPlayer2Board() {
        return player2Board;
    }

    public GameState(GameStatus status, PlayerBoard player1Board, PlayerBoard player2Board) {
        this.status = status;
        this.player1Board = player1Board;
        this.player2Board = player2Board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return status == gameState.status &&
                Objects.equals(player1Board, gameState.player1Board) &&
                Objects.equals(player2Board, gameState.player2Board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, player1Board, player2Board);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "status=" + status +
                ", player1Board=" + player1Board +
                ", player2Board=" + player2Board +
                '}';
    }
}
