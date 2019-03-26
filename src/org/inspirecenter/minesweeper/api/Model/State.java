package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;

public class State {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;

    private final int width;
    private final int height;

    private CellState[][] cells;

    public State(int width, int height) throws InvalidCellReferenceException {

        if (width < 0 || height < 0) {
            throw new InvalidCellReferenceException("Cannot initialize state with: width=" + width + ", height=" + height);
        }

        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
    }

    public State() throws InvalidCellReferenceException {
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
