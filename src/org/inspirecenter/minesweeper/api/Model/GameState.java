package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;

public abstract class GameState {

    protected final int width;
    protected final int height;
    protected CellState[][] cells;

    public GameState(int width, int height) throws InvalidCellReferenceException {

        if (width < 0 || height < 0) {
            throw new InvalidCellReferenceException("The game state with width: " + width + ", height: " + height + " is not valid.");
        }

        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
    }

    public CellState[][] getCells() {
        return cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
