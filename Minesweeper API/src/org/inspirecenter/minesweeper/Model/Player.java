package org.inspirecenter.minesweeper.Model;

import org.inspirecenter.minesweeper.Model.Solver.MinesweeperSolver;

public class Player {

    private final String name;
    private final Class<? extends MinesweeperSolver> minesweeperSolver;

    public Player(String name, Class<? extends MinesweeperSolver> minesweeperSolver) {
        this.name = name;
        this.minesweeperSolver = minesweeperSolver;
    }

    public String getName() {
        return name;
    }

    public Class<? extends MinesweeperSolver> getMinesweeperSolver() {
        return minesweeperSolver;
    }

}
