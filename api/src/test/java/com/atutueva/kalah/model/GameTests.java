package com.atutueva.kalah.model;

import com.atutueva.kalah.exception.GameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTests {
    @Test
    public void initGameWith6PitsAndEmptyKalahAndZeroIndexOfFirstPlayer() {
        Game game = Game.newGame();

        GameState expected = new GameStateBuilder(6)
                .player1Pits(6, 6, 6, 6, 6, 6)
                .player1Kalah(0)
                .player2Pits(6, 6, 6, 6, 6, 6)
                .player2Kalah(0)
                .status(GameStatus.PLAYER1_TURN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void throwExceptionForKalahIndexAsPitIndex() {
        Game game = Game.newGame();
        int inputPitIndex = 6;

        assertThrows(GameException.class, () -> {
            game.makeMove(inputPitIndex);
        });
    }

    @Test
    public void throwExceptionForNegativeValueAsPitIndex() {
        Game game = Game.newGame();
        int inputPitIndex = -1;

        assertThrows(GameException.class, () -> {
            game.makeMove(inputPitIndex);
        });
    }

    @Test
    public void throwExceptionForPitIndexWithZeroStone() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(0, 1, 0, 4, 1, 1)
                .player1Kalah(20)
                .player2Pits(2, 1, 1, 1, 5, 5)
                .player2Kalah(30)
                .status(GameStatus.PLAYER1_TURN);


        Game game = Game.fromState(state);

        assertThrows(GameException.class, () -> {
            game.makeMove(0);
        });
    }

    @Test
    public void throwExceptionIwGameIsAlreadyOver() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 0, 0)
                .player1Kalah(40)
                .player2Pits(0, 0, 0, 0, 0, 0)
                .player2Kalah(32)
                .status(GameStatus.PLAYER1_WIN);

        Game game = Game.fromState(state);

        assertThrows(GameException.class, () -> {
            game.makeMove(0);
        });
    }

    @Test
    public void move6StonesFromSecondPit() {
        Game game = Game.newGame();
        game.makeMove(1);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(6, 0, 7, 7, 7, 7)
                .player1Kalah(1)
                .player2Pits(7, 6, 6, 6, 6, 6)
                .player2Kalah(0)
                .status(GameStatus.PLAYER2_TURN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void move6StonesFromLastPitTakeTurn() {
        Game game = Game.newGame();
        game.makeMove(5);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(6, 6, 6, 6, 6, 0)
                .player1Kalah(1)
                .player2Pits(7, 7, 7, 7, 7, 6)
                .player2Kalah(0)
                .status(GameStatus.PLAYER2_TURN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void doTwoСirclesWithoutRepeatingStep() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(1, 1, 1, 1, 1, 26)
                .player1Kalah(1)
                .player2Pits(6, 4, 10, 5, 5, 5)
                .player2Kalah(5)
                .status(GameStatus.PLAYER1_TURN);

        Game game = Game.fromState(state);
        game.makeMove(5);

        GameState actual = game.getState();

        GameState expected = new GameStateBuilder(6)
                .player1Pits(3, 3, 3, 3, 3, 2)
                .player1Kalah(3)
                .player2Pits(8, 6, 12, 7, 7, 7)
                .player2Kalah(5)
                .status(GameStatus.PLAYER2_TURN);

        assertEquals(expected, actual);
    }

    @Test
    public void notPutPitToOpponentKalah() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(3, 4, 4, 4, 4, 12)
                .player1Kalah(5)
                .player2Pits(1, 1, 1, 1, 1, 1)
                .player2Kalah(30)
                .status(GameStatus.PLAYER1_TURN);

        Game game = Game.fromState(state);

        game.makeMove(5);
        GameState actual = game.getState();

        GameState expected = new GameStateBuilder(6)
                .player1Pits(4, 5, 5, 5, 5, 0)
                .player1Kalah(6)
                .player2Pits(2, 2, 2, 2, 2, 2)
                .player2Kalah(30)
                .status(GameStatus.PLAYER2_TURN);

        assertEquals(expected, actual);
    }

    @Test
    public void player1OwnOpponentStones() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(0, 1, 0, 4, 1, 1)
                .player1Kalah(20)
                .player2Pits(2, 1, 1, 1, 5, 5)
                .player2Kalah(30)
                .status(GameStatus.PLAYER1_TURN);

        Game game = Game.fromState(state);
        game.makeMove(1);

        GameState actual = game.getState();

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 4, 1, 1)
                .player1Kalah(22)
                .player2Pits(2, 1, 1, 0, 5, 5)
                .player2Kalah(30)
                .status(GameStatus.PLAYER2_TURN);

        assertEquals(expected, actual);
    }

    @Test
    public void player1TakesAdditionalMove() {
        Game game = Game.newGame();
        game.makeMove(0);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 7, 7, 7, 7, 7)
                .player1Kalah(1)
                .player2Pits(6, 6, 6, 6, 6, 6)
                .player2Kalah(0)
                .status(GameStatus.PLAYER1_TURN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void player2Win() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(8, 2, 5, 5, 5, 5)
                .player1Kalah(10)
                .player2Pits(0, 0, 0, 0, 1, 0)
                .player2Kalah(31)
                .status(GameStatus.PLAYER2_TURN);

        Game game = Game.fromState(state);
        game.makeMove(4);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 0, 0)
                .player1Kalah(32)
                .player2Pits(0, 0, 0, 0, 0, 0)
                .player2Kalah(40)
                .status(GameStatus.PLAYER2_WIN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void player1WinWithHalfStonesInKalahWhenAllPitsAreEmpty() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 1, 0)
                .player1Kalah(31)
                .player2Pits(8, 2, 5, 5, 5, 5)
                .player2Kalah(10)
                .status(GameStatus.PLAYER1_TURN);

        Game game = Game.fromState(state);
        game.makeMove(4);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 0, 0)
                .player1Kalah(40)
                .player2Pits(0, 0, 0, 0, 0, 0)
                .player2Kalah(32)
                .status(GameStatus.PLAYER1_WIN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void Player1WinWithHalfStonesInKalah() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(0, 1, 1, 2, 4, 1)
                .player1Kalah(36)
                .player2Pits(5, 5, 5, 5, 3, 3)
                .player2Kalah(1)
                .status(GameStatus.PLAYER1_TURN);

        Game game = Game.fromState(state);
        game.makeMove(4);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 1, 1, 2, 0, 2)
                .player1Kalah(37)
                .player2Pits(6, 6, 5, 5, 3, 3)
                .player2Kalah(1)
                .status(GameStatus.PLAYER1_WIN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void drawAfterPlayer1Move() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 0, 1)
                .player1Kalah(35)
                .player2Pits(3, 3, 3, 1, 1, 5)
                .player2Kalah(20)
                .status(GameStatus.PLAYER1_TURN);

        Game game = Game.fromState(state);
        game.makeMove(5);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 0, 0)
                .player1Kalah(36)
                .player2Pits(0, 0, 0, 0, 0, 0)
                .player2Kalah(36)
                .status(GameStatus.DRAW);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }

    @Test
    public void player1WinAfterOpponentStep() {
        GameState state = new GameStateBuilder(6)
                .player1Pits(11, 5, 7, 0, 1, 0)
                .player1Kalah(36)
                .player2Pits(1, 0, 0, 0, 0, 0)
                .player2Kalah(11)
                .status(GameStatus.PLAYER2_TURN);

        Game game = Game.fromState(state);
        game.makeMove(0);

        GameState expected = new GameStateBuilder(6)
                .player1Pits(0, 0, 0, 0, 0, 0)
                .player1Kalah(59)
                .player2Pits(0, 0, 0, 0, 0, 0)
                .player2Kalah(13)
                .status(GameStatus.PLAYER1_WIN);

        GameState actual = game.getState();

        assertEquals(expected, actual);
    }
}
