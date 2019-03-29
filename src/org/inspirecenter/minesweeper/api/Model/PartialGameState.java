package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;

public class PartialGameState extends GameState {

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_STARTING_X = 0;
    public static final int DEFAULT_STARTING_Y = 0;

    private final int startingX;
    private final int startingY;
    private UserState userState; //TODO WHY IS THIS NEEDED?

    public PartialGameState(int width, int height, int startingX, int startingY, FullGameState entireFullGameState) throws InvalidCellReferenceException {

        super(width, height);

        if (startingX + width > entireFullGameState.getWidth() || startingY + height > entireFullGameState.getHeight()
                || startingX < 0 || startingY < 0) {
            throw new InvalidCellReferenceException("The partial state with x: " + startingX + ", y: " + startingY + ", width: " + width + ", height: " + height + " is not valid.");
        }

        this.cells = new CellState[width][height];
        this.startingX = startingX;
        this.startingY = startingY;

        //Copy the partial state from the full state:
        for (int x = startingX; (x < startingX + width); x++) {
            for (int y = startingY; (y < startingY + height); y++) {
                cells[x - startingX][y - startingY] = entireFullGameState.getCells()[x][y];
            }
        }
    }

    public PartialGameState(int startingX, int startingY, FullGameState entireFullGameState) throws InvalidCellReferenceException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, startingX, startingY, entireFullGameState);
    }

    public PartialGameState(FullGameState entireFullGameState) throws InvalidCellReferenceException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_STARTING_X, DEFAULT_STARTING_Y, entireFullGameState);
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public UserState getUserState() {
        return userState;
    }

}
