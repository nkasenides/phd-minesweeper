package org.inspirecenter.minesweeper.API;

import org.inspirecenter.minesweeper.Model.GameState;
import org.inspirecenter.minesweeper.Model.PartialBoardState;

public class RevealBundle {

    private final PartialBoardState partialGameState;
    private final GameState gameState;

    public RevealBundle(PartialBoardState partialGameState, GameState gameState) {
        this.partialGameState = partialGameState;
        this.gameState = gameState;
    }

    public PartialBoardState getPartialGameState() {
        return partialGameState;
    }

    public GameState getGameState() {
        return gameState;
    }

}
