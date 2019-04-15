package org.inspirecenter.minesweeper.Model.Solver;

import org.inspirecenter.minesweeper.Model.*;

import java.util.Random;

public class RandomMinesweeperSolver extends MinesweeperSolver {

    public static final String NAME = "random";
    public int xOffset = 0;
    public int yOffset = 0;

    public RandomMinesweeperSolver(Game game, PartialStatePreference partialStatePreference) {
        super(game, partialStatePreference);
    }

    @Override
    public Move solve() {

        if (moves.size() == 0) {
            Random random = new Random();
            int randomX = random.nextInt(partialStatePreference.getWidth());
            int randomY = random.nextInt(partialStatePreference.getHeight());
            Move move = new Move(randomX, randomY, MoveType.REVEAL);
            moves.add(move);
            return move;
        }
        else {
            Move previousMove = moves.get(moves.size() - 1);
           //TODO
            return new Move(0, 0, MoveType.NONE);
        }

    }

}
