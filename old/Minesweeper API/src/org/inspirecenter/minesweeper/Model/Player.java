package org.inspirecenter.minesweeper.Model;

import org.inspirecenter.minesweeper.Model.Solver.MinesweeperSolver;

public class Player {

    private final String name;
    private final MinesweeperSolver minesweeperSolver;

    public Player(String name, MinesweeperSolver minesweeperSolver) {
        this.name = name;
        this.minesweeperSolver = minesweeperSolver;
    }

    public String getName() {
        return name;
    }

    public MinesweeperSolver getMinesweeperSolver() {
        return minesweeperSolver;
    }

}
