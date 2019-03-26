package org.inspirecenter.minesweeper.api.Model;

public class CellState {

    private MineState mineState;
    private RevealState revealState;

    public CellState(MineState mineState, RevealState revealState) {
        this.mineState = mineState;
        this.revealState = revealState;
    }

    public MineState getMineState() {
        return mineState;
    }

    public RevealState getRevealState() {
        return revealState;
    }

    public void setMineState(MineState mineState) {
        this.mineState = mineState;
    }

    public void setRevealState(RevealState revealState) {
        this.revealState = revealState;
    }

}
