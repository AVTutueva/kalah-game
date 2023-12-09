package com.atutueva.kalah.model;

import com.atutueva.kalah.utils.Utils;

public class GameStateBuilder {
    private final int initStonesPerPit;
    private int kalah1 = 0;
    private int kalah2 = 0;
    private int[] pits1;
    private int[] pits2;

    private GameStatus status;

    public GameStateBuilder(int stonesPerPit) {
        this.initStonesPerPit = stonesPerPit;
    }

    public GameStateBuilder player1Pits(int... stones) {
        validateStones(stones);
        this.pits1 = stones;
        return this;
    }

    public GameStateBuilder player2Pits(int... stones) {
        validateStones(stones);
        this.pits2 = stones;
        return this;
    }

    public GameStateBuilder player1Kalah(int stoneCount) {
        this.kalah1 = stoneCount;
        return this;
    }

    public GameStateBuilder player2Kalah(int stoneCount) {
        this.kalah2 = stoneCount;
        return this;
    }

    public GameState status(GameStatus status) {
        this.status = status;
        return build();
    }

    private GameState build() {
        int totalStones = Utils.arraySum(pits1, pits1.length) + Utils.arraySum(pits2, pits2.length) + kalah1 + kalah2;
        int requiredStones = 2 * Game.PINTS_PER_PLAYER * initStonesPerPit;
        if (totalStones != requiredStones) {
            throw new IllegalStateException("Total stones number in game " + totalStones + " is invalid. Required number is " + requiredStones);
        }

        PlayerBoard playerBoard1 = new PlayerBoard(pits1, kalah1);
        PlayerBoard playerBoard2 = new PlayerBoard(pits2, kalah2);

        return new GameState(status, playerBoard1, playerBoard2);
    }

    private void validateStones(int... stones) {
        if (stones.length != Game.PINTS_PER_PLAYER) throw new IllegalArgumentException("Stones size must fit game size");
        for (int stone : stones) {
            if (stone < 0 || stone > 2 * Game.PINTS_PER_PLAYER * initStonesPerPit)
                throw new IllegalStateException("Stones number is invalid");
        }
    }
}
