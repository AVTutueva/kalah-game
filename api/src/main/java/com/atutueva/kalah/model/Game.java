package com.atutueva.kalah.model;

import com.atutueva.kalah.exception.GameGeneralException;
import com.atutueva.kalah.utils.Utils;

import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Game {
    private final int[][] board;
    private GameStatus status;
    public static final int PINTS_PER_PLAYER = 6;
    private static final int INIT_STONES_PER_PIT = 6;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private Game(GameState state) {
        board = new int[2][PINTS_PER_PLAYER + 1];
        initPlayerData(state.getPlayer1Board(), 0);
        initPlayerData(state.getPlayer2Board(), 1);
        status = state.getStatus();
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
                .status(GameStatus.PLAYER1_TURN);

        return new Game(state);
    }

    private void initPlayerData(PlayerBoard playerBoard, int index) {
        Pit[] playerPits = playerBoard.getPits();
        int[] boardRow = new int[playerPits.length + 1];

        for (int i = 0; i < playerPits.length; i++) {
            boardRow[i] = playerPits[i].getValue();
        }

        boardRow[PINTS_PER_PLAYER] = playerBoard.getKalah();
        board[index] = boardRow;
    }

    public GameState makeMove(int pitIndex) {
        try {
            lock.writeLock().lock();
            if (status == GameStatus.PLAYER1_WIN || status == GameStatus.PLAYER2_WIN || status == GameStatus.DRAW)
                throw new GameGeneralException("Game is already over");

            if (pitIndex >= PINTS_PER_PLAYER || pitIndex < 0) {
                throw new GameGeneralException("Pit Index " + pitIndex + " is invalid. Pit index should be in range [0, " + (PINTS_PER_PLAYER - 1) + "]");
            }

            int initPlayer = getPlayerIndex();

            int currPlayer = initPlayer;
            int[] currentBoard = board[currPlayer];

            int stonesToMove = currentBoard[pitIndex];
            if (stonesToMove == 0)
                throw new GameGeneralException("Invalid move with index=" + pitIndex + ". Stones to move number should be greater than zero");

            // take off stones from pit
            currentBoard[pitIndex] = 0;
            int i = pitIndex + 1;

            // move stones
            while (stonesToMove > 0) {
                // skip opponent kalah
                if (!(currPlayer != initPlayer && i == PINTS_PER_PLAYER)) {
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

            if (!isKalah) {
                int indexToOwn = PINTS_PER_PLAYER - 1 - i;
                int[] opponentBoard = board[(initPlayer + 1) % 2];
                int opponentStones = opponentBoard[indexToOwn];

                boolean isInitPlayer = currPlayer == initPlayer;
                boolean oneStoneInLastPit = currentBoard[i] == 1;

                if (oneStoneInLastPit && isInitPlayer && opponentStones != 0) {
                    currentBoard[PINTS_PER_PLAYER] += opponentStones + 1;
                    currentBoard[i] = 0;
                    opponentBoard[indexToOwn] = 0;
                }

                status = (status == GameStatus.PLAYER1_TURN) ? GameStatus.PLAYER2_TURN : GameStatus.PLAYER1_TURN;
            }

            isOverCheck(initPlayer);
            return buildState();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void isOverCheck(int initPlayerIndex) {
        int[] currentBoard = board[initPlayerIndex];
        int[] opponentBoard = board[(initPlayerIndex + 1) % 2];

        int halfOfTotalStones = PINTS_PER_PLAYER * INIT_STONES_PER_PIT;

        int currentPlayerPitsStones = Utils.subArraySum(currentBoard, currentBoard.length - 1);
        int opponentPitsStones = Utils.subArraySum(opponentBoard, opponentBoard.length - 1);

        if (currentPlayerPitsStones == 0 || opponentPitsStones == 0) {
            currentBoard[PINTS_PER_PLAYER] += currentPlayerPitsStones;
            opponentBoard[PINTS_PER_PLAYER] += opponentPitsStones;

            resetPits(currentBoard);
            resetPits(opponentBoard);
        }

        boolean bothHaveEqualStones = currentBoard[PINTS_PER_PLAYER] == opponentBoard[PINTS_PER_PLAYER];
        if (bothHaveEqualStones && currentBoard[PINTS_PER_PLAYER] == halfOfTotalStones) {
            status = GameStatus.DRAW;
        }
        if (currentBoard[PINTS_PER_PLAYER] > halfOfTotalStones) {
            status = (initPlayerIndex == 0) ? GameStatus.PLAYER1_WIN : GameStatus.PLAYER2_WIN;
        }
        if (opponentBoard[PINTS_PER_PLAYER] > halfOfTotalStones) {
            status = (initPlayerIndex == 0) ? GameStatus.PLAYER2_WIN : GameStatus.PLAYER1_WIN;
        }
    }

    private void resetPits(int[] board) {
        for (int j = 0; j < PINTS_PER_PLAYER; j++) {
            board[j] = 0;
        }
    }

    private int getPlayerIndex() {
        switch (status) {
            case PLAYER1_TURN -> {
                return 0;
            }
            case PLAYER2_TURN -> {
                return 1;
            }
            default -> {
                throw new GameGeneralException("Game is already over");
            }
        }
    }

    public GameState getState() {
        try {
            lock.readLock().lock();
            return buildState();
        } finally {
            lock.readLock().unlock();
        }
    }

    private GameState buildState() {
        return new GameStateBuilder(PINTS_PER_PLAYER)
                .player1Pits(Arrays.copyOfRange(board[0], 0, PINTS_PER_PLAYER))
                .player1Kalah(board[0][PINTS_PER_PLAYER])
                .player2Pits(Arrays.copyOfRange(board[1], 0, PINTS_PER_PLAYER))
                .player2Kalah(board[1][PINTS_PER_PLAYER])
                .status(status);
    }
}
