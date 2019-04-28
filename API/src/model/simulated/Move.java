package model.simulated;

public class Move {

    private final MoveType moveType;
    private final int x;
    private final int y;

    public Move(MoveType moveType, int x, int y) {
        this.moveType = moveType;
        this.x = x;
        this.y = y;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
