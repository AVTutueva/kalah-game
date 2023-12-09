package com.atutueva.kalah.model;

import com.atutueva.kalah.utils.Utils;

import java.util.Arrays;

public class Game {
    private final int[][] board;
    private GameStatus status;
    public static final int PINTS_PER_PLAYER = 6;
    private static final int INIT_STONES_PER_PIT = 6;

    private Game(GameState state) {
        board = new int[2][PINTS_PER_PLAYER + 1];
        initPlayerData(state.getPlayer1Board(), 0);
        initPlayerData(state.getPlayer2Board(), 1);
        status = state.getStatus();
    }

    private void initPlayerData(PlayerBoard playerBoard, int index) {
        int[] playerPits = playerBoard.getPits();
        board[index] = Arrays.copyOf(playerPits, playerPits.length + 1);
        board[index][PINTS_PER_PLAYER] = playerBoard.getKalah();
    }

    public static Game fromState(GameState state) {
        return new Game(state);
    }

    public static Game newGame() {
        int[] player1Stones = new int[PINTS_PER_PLAYER];
        Arrays.fill(player1Stones, INIT_STONES_PER_PIT);

        int[] player2Stones = new int[PINTS_PER_PLAYER];
        Arrays.fill(player2Stones, INIT_STONES_PER_PIT);

        GameState state = new GameStateBuilder(INIT_STONES_PER_PIT)
                .player1Pits(player1Stones)
                .player1Kalah(0)
                .player2Pits(player2Stones)
                .player2Kalah(0)
                .status(GameStatus.PlAYER1_TURN);

        return new Game(state);
    }

    public GameState makeMove(int pitIndex) {
        if (status == GameStatus.PLAYER1_WIN || status == GameStatus.PLAYER2_WIN || status == GameStatus.STANDOFF)
            throw new IllegalStateException("Game is already over");

        if (pitIndex >= PINTS_PER_PLAYER || pitIndex < 0) throw new IllegalArgumentException("Pit Index is invalid");

        int initPlayer = getPlayerIndex();
        int currPlayer = initPlayer;
        int[] currentBoard = board[currPlayer];

        int stonesToMove = currentBoard[pitIndex];
        if (stonesToMove == 0) throw new IllegalArgumentException("Invalid move. Stones to move number should be than zero");

        // take off stones from pit
        currentBoard[pitIndex] = 0;
        int i = pitIndex + 1;

        // move stones
        while (stonesToMove > 0) {
            // skip opponent kalah
            if (!(currPlayer != initPlayer && i == PINTS_PER_PLAYER)){
                currentBoard[i]++;
                stonesToMove--;
            }

            if (stonesToMove == 0) break;

            if (i == PINTS_PER_PLAYER) {
                i = 0;
                currPlayer = (currPlayer + 1) % 2;
                currentBoard = board[currPlayer];
            } else {
                i++;
            }
        }


        // own opponent stones
        boolean isKalah = i == PINTS_PER_PLAYER;

        int[] opponentBoard = board[(currPlayer + 1) % 2];
        if (!isKalah) {
            int opponentStones = opponentBoard[PINTS_PER_PLAYER - i];

            boolean isInitSide = currPlayer == initPlayer;
            boolean oneStoneInLastPit = currentBoard[i] == 1;

            if (oneStoneInLastPit && isInitSide && opponentStones != 0){
                currentBoard[PINTS_PER_PLAYER] += opponentStones + 1;
                currentBoard[i] = 0;
                opponentBoard[PINTS_PER_PLAYER - i] = 0;
            }

            status = (status == GameStatus.PlAYER1_TURN) ? GameStatus.PlAYER2_TURN : GameStatus.PlAYER1_TURN;
        }


        // Check win
        int halfOfTotalStones = PINTS_PER_PLAYER * INIT_STONES_PER_PIT;

        int currentPlayerPitsStones = Utils.arraySum(currentBoard, currentBoard.length - 1);
        int opponentPitsStones = Utils.arraySum(opponentBoard, opponentBoard.length - 1);

        if (currentPlayerPitsStones == 0 || opponentPitsStones == 0){
            currentBoard[PINTS_PER_PLAYER] += currentPlayerPitsStones;
            opponentBoard[PINTS_PER_PLAYER] += opponentPitsStones;
            for (int j = 0; j < PINTS_PER_PLAYER; j++){
                currentBoard[j] = 0;
                opponentBoard[j] = 0;
            }
        }

        boolean bothHaveEqualStones = currentBoard[PINTS_PER_PLAYER] == opponentBoard[PINTS_PER_PLAYER];
        if (bothHaveEqualStones && currentBoard[PINTS_PER_PLAYER] == halfOfTotalStones){
            status = GameStatus.STANDOFF;
        }
        if (currentBoard[PINTS_PER_PLAYER] > halfOfTotalStones) {
            status = (initPlayer == 0) ? GameStatus.PLAYER1_WIN : GameStatus.PLAYER2_WIN;
        }

        return getState();
    }


    private int getPlayerIndex(){
        switch (status){
            case PlAYER1_TURN -> {
                return 0;
            }
            case PlAYER2_TURN -> {
                return 1;
            }
            default -> {
                throw new IllegalArgumentException("Game is over");
            }
        }
    }

    public GameState getState() {
        return new GameStateBuilder(PINTS_PER_PLAYER)
                .player1Pits(Arrays.copyOfRange(board[0], 0, PINTS_PER_PLAYER))
                .player1Kalah(board[0][PINTS_PER_PLAYER])
                .player2Pits(Arrays.copyOfRange(board[1], 0, PINTS_PER_PLAYER))
                .player2Kalah(board[1][PINTS_PER_PLAYER])
                .status(status);
    }
}
