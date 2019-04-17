package org.inspirecenter.minesweeper.Model.Solver;

public class Move {

    private final int x;
    private final int y;
    private final MoveType moveType;

    public Move(int x, int y, MoveType moveType) {
        this.x = x;
        this.y = y;
        this.moveType = moveType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MoveType getMoveType() {
        return moveType;
    }

}
