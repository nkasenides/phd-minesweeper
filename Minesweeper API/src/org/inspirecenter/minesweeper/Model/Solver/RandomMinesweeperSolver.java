package org.inspirecenter.minesweeper.Model.Solver;

import org.inspirecenter.minesweeper.Model.*;

import java.util.Random;

public class RandomMinesweeperSolver extends MinesweeperSolver {

    public RandomMinesweeperSolver(Game game, PartialStatePreference partialStatePreference) {
        super(game, partialStatePreference);
        registerSolver(this.getClass());
    }

    @Override
    public Move solve() {

        Random random = new Random();
        int randomX = random.nextInt(partialStatePreference.getWidth());
        int randomY = random.nextInt(partialStatePreference.getHeight());

        if (game.)

        //TODO
        return new Move(0, 0, MoveType.NONE);

    }



}
