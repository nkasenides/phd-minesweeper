package org.inspirecenter.minesweeper.api.Model;

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
    private UserState userState;

    public PartialState(int width, int height, int startingX, int startingY) {
        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
        this.startingX = startingX;
        this.startingY = startingY;
    }

    public PartialState(int startingX, int startingY) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, startingX, startingY);
    }

    public PartialState() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_STARTING_X, DEFAULT_STARTING_Y);
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
