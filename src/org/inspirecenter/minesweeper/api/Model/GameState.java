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

    public boolean isValidCell(int x, int y) {
        return (x >= 0) && (x < width) && (y >= 0) && (y < height);
    }

    public int countAdjacentMines(int x, int y) {
        int count = 0;
    /*
        Count all the mines in the 8 adjacent
        cells

            N.W   N   N.E
              \   |   /
               \  |  /
            W----Cell----E
                 / | \
               /   |  \
            S.W    S   S.E

        N -->  North        (x-1, y)
        S -->  South        (x+1, y)
        E -->  East         (x, y+1)
        W -->  West         (x, y-1)
        N.E--> North-East   (x-1, y+1)
        N.W--> North-West   (x-1, y-1)
        S.E--> South-East   (x+1, y+1)
        S.W--> South-West   (x+1, y-1)
    */

        //----------- North ------------

        if (isValidCell(x - 1, y)) {
            if (cells[x - 1][y].isMined()) {
                count++;
            }
        }

        //----------- South ------------

        if (isValidCell(x + 1, y)) {
            if (cells[x + 1][y].isMined()) {
                count++;
            }
        }

        //----------- East ------------

        if (isValidCell(x, y + 1)) {
            if (cells[x][y + 1].isMined()) {
                count++;
            }
        }

        //----------- West ------------

        if (isValidCell(x, y - 1)) {
            if (cells[x][y - 1].isMined()) {
                count++;
            }
        }

        //----------- North-East ------------

        if (isValidCell(x - 1, y + 1)) {
            if (cells[x - 1][y + 1].isMined()) {
                count++;
            }
        }

        //----------- North-West ------------

        if (isValidCell(x - 1, y - 1)) {
            if (cells[x - 1][y - 1].isMined()) {
                count++;
            }
        }

        //----------- South-East ------------

        if (isValidCell(x + 1, y + 1)) {
            if (cells[x + 1][y + 1].isMined()) {
                count++;
            }
        }

        //----------- South-West ------------

        if (isValidCell(x + 1, y - 1)) {
            if (cells[x + 1][y - 1].isMined()) {
                count++;
            }
        }

        return (count);
    }

}
