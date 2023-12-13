package com.atutueva.kalah.model;

import java.util.Arrays;
import java.util.Objects;

public class PlayerBoard {
    private Pit[] pits;
    private int kalah;

    public PlayerBoard(int[] pits, int kalah) {
        this.pits = new Pit[pits.length];
        for (int i = 0; i < pits.length; i++) {
            this.pits[i] = new Pit(i, pits[i]);
        }
        this.kalah = kalah;
    }

    public Pit[] getPits() {
        return this.pits = Arrays.copyOf(pits, pits.length);
    }

    public int getKalah() {
        return kalah;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerBoard that = (PlayerBoard) o;
        return kalah == that.kalah && Arrays.equals(pits, that.pits);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(kalah);
        result = 31 * result + Arrays.hashCode(pits);
        return result;
    }

    @Override
    public String toString() {
        return "PlayerBoard{" +
                "pits=" + Arrays.toString(pits) +
                ", kalah=" + kalah +
                '}';
    }
}
