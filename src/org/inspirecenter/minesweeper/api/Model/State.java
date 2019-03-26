package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Model.CellState;

public class State {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private CellState[][] cells;

    public State(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
    }

    public State() {
        this.cells = new CellState[width][height];
    }
}
