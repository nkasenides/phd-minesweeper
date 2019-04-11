package org.inspirecenter.minesweeper.Model.Solver;

import org.inspirecenter.minesweeper.Model.*;

public class RandomMinesweeperSolver extends MinesweeperSolver {

    public RandomMinesweeperSolver(Game game, PartialStatePreference partialStatePreference) {
        super(game, partialStatePreference);
    }

    @Override
    public Move solve() {
        //TODO
        return new Move(0, 0, MoveType.NONE);

    }



}
