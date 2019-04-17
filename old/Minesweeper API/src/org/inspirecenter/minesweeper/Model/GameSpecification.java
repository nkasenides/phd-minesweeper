package org.inspirecenter.minesweeper.Model;

import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;

import java.util.UUID;

public class GameSpecification {

    public static final Difficulty DEFAULT_DIFFICULTY = Difficulty.MEDIUM;

    private final int maxPlayers; // max number of players
    private final int width;
    private final int height;
    private final String token;
    private final Difficulty difficulty;

    public GameSpecification(int maxPlayers, int width, int height, Difficulty difficulty) throws InvalidGameSpecificationException {

        if (maxPlayers < 1 || width < 0 || height < 0 || difficulty == null) {
            throw new InvalidGameSpecificationException("Game specification cannot be initialized (Invalid parameters)");
        }

        this.maxPlayers = maxPlayers;
        this.width = width;
        this.height = height;
        this.token = UUID.randomUUID().toString();
        this.difficulty = difficulty;
    }

    public GameSpecification(int maxPlayers, int width, int height) throws InvalidGameSpecificationException {
        this(maxPlayers, width, height, DEFAULT_DIFFICULTY);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getToken() {
        return token;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

}
