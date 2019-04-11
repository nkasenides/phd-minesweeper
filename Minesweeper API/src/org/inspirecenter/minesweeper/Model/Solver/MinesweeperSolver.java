package org.inspirecenter.minesweeper.Model.Solver;

import org.inspirecenter.minesweeper.Model.Game;
import org.inspirecenter.minesweeper.Model.PartialStatePreference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public abstract class MinesweeperSolver {

    protected ArrayList<Move> moves;
    protected transient final Game game; //TODO ELIMINATE?
    protected final PartialStatePreference partialStatePreference;
    public static HashSet<Class <? extends MinesweeperSolver>> ALL_SOLVERS = new HashSet<>();

    public MinesweeperSolver(Game game, PartialStatePreference partialStatePreference) {
        this.moves = new ArrayList<>();
        this.game = game;
        this.partialStatePreference = partialStatePreference;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public boolean addMove(Move move) {
        return this.moves.add(move);
    }

    public Game getGame() {
        return game;
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

    public abstract Move solve();

    public static void registerSolver(Class <? extends MinesweeperSolver> solver) {
        ALL_SOLVERS.add(solver);
    }

    public static Class <? extends MinesweeperSolver> getSolverFromName(String name) {
        for (Class<? extends MinesweeperSolver> c : ALL_SOLVERS) {
            if (name.toLowerCase().equals(c.getSimpleName().toLowerCase())) {
                return c;
            }
        }
        return null;
    }

}
