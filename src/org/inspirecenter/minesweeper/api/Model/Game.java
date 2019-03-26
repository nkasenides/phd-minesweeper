package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private GameSpecification gameSpecification;
    private FullGameState fullGameState;
    private ArrayList<Player> players;

    public Game(GameSpecification gameSpecification) {
        this.gameSpecification = gameSpecification;
        try {
            fullGameState = new FullGameState(gameSpecification.getWidth(), gameSpecification.getHeight());
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

    public FullGameState getFullGameState() {
        return fullGameState;
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
        for (int x = 0; x < fullGameState.getCells().length; x++) {
            for (int y = 0; y < fullGameState.getCells()[x].length; y++) {
                fullGameState.getCells()[x][y] = new CellState(MineState.NOT_MINED, RevealState.COVERED);
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
            if (fullGameState.getCells()[randomX][randomY].getMineState() == MineState.NOT_MINED) {
                fullGameState.getCells()[randomX][randomY].setMineState(MineState.MINED);
                generatedMines++;
            }
        } while (generatedMines < numberOfMines);
    }

}
