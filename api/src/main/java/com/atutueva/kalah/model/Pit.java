package com.atutueva.kalah.model;

import java.util.Objects;

public class Pit {
    private int index;
    private int value;

    public Pit(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Pit{" +
                "index=" + index +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pit pit = (Pit) o;
        return index == pit.index && value == pit.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, value);
    }
}
