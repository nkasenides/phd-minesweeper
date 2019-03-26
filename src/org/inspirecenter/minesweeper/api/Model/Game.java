package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private GameSpecification gameSpecification;
    private State state;
    private ArrayList<Player> players;

    public Game(GameSpecification gameSpecification) {
        this.gameSpecification = gameSpecification;
        try {
            state = new State(gameSpecification.getWidth(), gameSpecification.getHeight());
            players = new ArrayList<>();
            initializeMatrix();
            generateMines();
        } catch (InvalidCellReferenceException e) {
            e.printStackTrace();
        }
    }

    public GameSpecification getGameSpecification() {
        return gameSpecification;
    }

    public State getState() {
        return state;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean addPlayer(Player player) {
        if (players.size() < gameSpecification.getMaxPlayers()) {
            return players.add(player);
        }
        return false;
    }

    private void initializeMatrix() {
        for (int x = 0; x < state.getCells().length; x++) {
            for (int y = 0; y < state.getCells()[x].length; y++) {
                state.getCells()[x][y] = new CellState(MineState.NOT_MINED, RevealState.COVERED);
            }
        }
    }

    private void generateMines() {
        Random random = new Random();
        final int numberOfMines = Math.round(gameSpecification.getWidth() * gameSpecification.getHeight() * gameSpecification.getDifficulty().getMineRatio());
        int generatedMines = 0;
        do {
            int randomX = random.nextInt(gameSpecification.getWidth());
            int randomY = random.nextInt(gameSpecification.getHeight());
            if (state.getCells()[randomX][randomY].getMineState() == MineState.NOT_MINED) {
                state.getCells()[randomX][randomY].setMineState(MineState.MINED);
                generatedMines++;
            }
        } while (generatedMines < numberOfMines);
    }

    public void printAsMatrix() {
        for (int x = 0; x < state.getCells().length; x++) {
            for (int y = 0; y < state.getCells()[x].length; y++) {
                if (state.getCells()[x][y].getMineState() == MineState.MINED) System.out.print("*");
                else System.out.print("_");
            }
            System.out.println();
        }
    }

}
