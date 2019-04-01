package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;

public class FullBoardState extends BoardState {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;

    public FullBoardState(int width, int height) throws InvalidCellReferenceException {
        super(width, height);
    }

    public FullBoardState() throws InvalidCellReferenceException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
