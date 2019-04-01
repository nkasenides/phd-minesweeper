package org.inspirecenter.minesweeper.api.API;

import org.inspirecenter.minesweeper.api.Model.GameState;
import org.inspirecenter.minesweeper.api.Model.PartialBoardState;

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
