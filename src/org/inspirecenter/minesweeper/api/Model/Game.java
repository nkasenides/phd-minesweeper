package org.inspirecenter.minesweeper.api.Model;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;
import org.inspirecenter.minesweeper.api.Util.Storage;

import java.util.Random;

public class Game {

    private GameSpecification gameSpecification;
    private FullBoardState fullBoardState;
    private GameState gameState;

    public static Game findGameSpecification(String token) {
        for (final Game g : Storage.GAMES) {
            if (g.getGameSpecification().getToken().equals(token)) return g;
        }
        return null;
    }

    public Game(GameSpecification gameSpecification) {
        this.gameSpecification = gameSpecification;
        this.gameState = GameState.NOT_STARTED;
        try {
            fullBoardState = new FullBoardState(gameSpecification.getWidth(), gameSpecification.getHeight());
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

    public FullBoardState getFullBoardState() {
        return fullBoardState;
    }

    private void initializeMatrix() {
        for (int x = 0; x < fullBoardState.getCells().length; x++) {
            for (int y = 0; y < fullBoardState.getCells()[x].length; y++) {
                fullBoardState.getCells()[x][y] = new CellState(false);
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
            if (!fullBoardState.getCells()[randomX][randomY].isMined()) {
                fullBoardState.getCells()[randomX][randomY].setMined(true);
                generatedMines++;
            }
        } while (generatedMines < numberOfMines);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int countFlaggedMines() {
        int count = 0;
        for (int x = 0; x < fullBoardState.getWidth(); x++) {
            for (int y = 0; y < fullBoardState.getHeight(); y++) {
                if (fullBoardState.getCells()[x][y].isMined() && fullBoardState.getCells()[x][y].getRevealState() == RevealState.FLAGGED) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countMines() {
        int count = 0;
        for (int x = 0; x < fullBoardState.getWidth(); x++) {
            for (int y = 0; y < fullBoardState.getHeight(); y++) {
                if (fullBoardState.getCells()[x][y].isMined()) {
                    count++;
                }
            }
        }
        return count;
    }

    public GameState computeGameState() {

        if (gameState != GameState.STARTED) {
            return gameState;
        }

        int covered = 0;
        final int flaggedMines = countFlaggedMines();
        final int totalMines = countMines();

        for (int x = 0; x < fullBoardState.getWidth(); x++) {
            for (int y = 0; y < fullBoardState.getHeight(); y++) {

                if (fullBoardState.cells[x][y].isMined() && fullBoardState.cells[x][y].getRevealState() == RevealState.REVEALED_MINE) {
                    return GameState.ENDED_LOST;
                }

                if (fullBoardState.cells[x][y].getRevealState() == RevealState.COVERED) {
                    covered++;
                }

            }
        }

        if (covered == 0 && flaggedMines == totalMines ) {
            return GameState.ENDED_WON;
        }

        return GameState.STARTED;

    }

}
