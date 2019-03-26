package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Model.CellState;

public class State {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;

    private final int width;
    private final int height;

    private CellState[][] cells;

    public State(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
    }

    public State() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public CellState[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
