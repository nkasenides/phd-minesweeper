package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;
import org.inspirecenter.minesweeper.api.Util.Storage;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private GameSpecification gameSpecification;
    private FullGameState fullGameState;

    public static Game findGameSpecification(String token) {
        for (final Game g : Storage.GAMES) {
            if (g.getGameSpecification().getToken().equals(token)) return g;
        }
        return null;
    }

    public Game(GameSpecification gameSpecification) {
        this.gameSpecification = gameSpecification;
        try {
            fullGameState = new FullGameState(gameSpecification.getWidth(), gameSpecification.getHeight());
            initializeMatrix();
            generateMines();
            Storage.GAMES.add(this);
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

    private void initializeMatrix() {
        for (int x = 0; x < fullGameState.getCells().length; x++) {
            for (int y = 0; y < fullGameState.getCells()[x].length; y++) {
                fullGameState.getCells()[x][y] = new CellState(false);
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
            if (!fullGameState.getCells()[randomX][randomY].isMined()) {
                fullGameState.getCells()[randomX][randomY].setMined(true);
                generatedMines++;
            }
        } while (generatedMines < numberOfMines);
    }

}
