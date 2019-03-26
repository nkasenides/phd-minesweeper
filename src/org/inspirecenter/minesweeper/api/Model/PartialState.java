package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;

public class PartialState {

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_STARTING_X = 0;
    public static final int DEFAULT_STARTING_Y = 0;

    private final int width;
    private final int height;
    private final int startingX;
    private final int startingY;
    private CellState[][] cells;
    private UserState userState; //TODO WHY IS THIS NEEDED?

    public PartialState(int width, int height, int startingX, int startingY, State entireState) throws InvalidCellReferenceException {

        if (startingX + width >= entireState.getWidth() || startingY + height >= entireState.getHeight()
                || startingX < 0 || startingY < 0 || width < 0 || height < 0) {
            throw new InvalidCellReferenceException("The partial state with x: " + startingX + ", y: " + startingY + ", width: " + width + ", height: " + height + " is not valid.");
        }

        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
        this.startingX = startingX;
        this.startingY = startingY;

        //Copy the sub-state from the original state:
        for (int x = startingX; x < entireState.getWidth() || x < width; x++) {
            for (int y = startingY; y < entireState.getHeight() || y < height; y++) {
                cells[x][y] = entireState.getCells()[x][y];
            }
        }
    }

    public PartialState(int startingX, int startingY, State entireState) throws InvalidCellReferenceException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, startingX, startingY, entireState);
    }

    public PartialState(State entireState) throws InvalidCellReferenceException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_STARTING_X, DEFAULT_STARTING_Y, entireState);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public CellState[][] getCells() {
        return cells;
    }

    public UserState getUserState() {
        return userState;
    }

}
