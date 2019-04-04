package org.inspirecenter.minesweeper.Model.Solver;

import org.inspirecenter.minesweeper.Model.Game;
import org.inspirecenter.minesweeper.Model.PartialStatePreference;

import java.util.ArrayList;

public abstract class MinesweeperSolver {

    protected ArrayList<Move> moves;
    protected transient final Game game; //TODO ELIMINATE
    protected final PartialStatePreference partialStatePreference;

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

}
