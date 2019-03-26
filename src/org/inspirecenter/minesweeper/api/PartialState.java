package org.inspirecenter.minesweeper.api;

public class PartialState {

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private CellState[][] cells;

    private int startingX = 0;
    private int startingY = 0;

    private UserState userState;

    public PartialState(int width, int height, int startingX, int startingY) {
        this.width = width;
        this.height = height;
        this.cells = new CellState[width][height];
        this.startingX = startingX;
        this.startingY = startingY;
    }

    public PartialState() {
        this.cells = new CellState[width][height];
    }

}
