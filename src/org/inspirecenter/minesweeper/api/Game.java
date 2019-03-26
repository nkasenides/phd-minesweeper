package org.inspirecenter.minesweeper.api;

import java.util.UUID;

public class Game {

    public static final double DEFAULT_DIFFICULTY = 0.15;

    private int numOfPlayers; // max number of players
    private int width;
    private int height;
    private String token;
    private double difficulty; // percentage of mines - must be 0.5 ... 0.25 (0.1 is easy, 0.15 is medium, 0.2 is hard)

    public Game(int numOfPlayers, int width, int height, String token, double difficulty) {
        this.numOfPlayers = numOfPlayers;
        this.width = width;
        this.height = height;
        this.token = UUID.randomUUID().toString();
        this.difficulty = difficulty;
    }

    public Game(int numOfPlayers, int width, int height, String token) {
        this(numOfPlayers, width, height, token, DEFAULT_DIFFICULTY);
    }
}
