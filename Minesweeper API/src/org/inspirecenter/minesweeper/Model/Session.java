package org.inspirecenter.minesweeper.Model;

import java.util.UUID;

public class Session {

    private final String sessionID;
    private final PartialStatePreference partialStatePreference;
    private final String playerName;
    private final Game game;
    private int positionX;
    private int positionY;

    public Session(PartialStatePreference partialStatePreference, String playerName, Game game) {
        this.sessionID = UUID.randomUUID().toString();
        this.partialStatePreference = partialStatePreference;
        this.playerName = playerName;
        this.game = game;
        this.positionX = 0;
        this.positionY = 0;
    }

    public String getSessionID() {
        return sessionID;
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Game getGame() {
        return game;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}
