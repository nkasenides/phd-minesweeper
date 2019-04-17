package org.inspirecenter.minesweeper.API;

public class JoinBundle {

    private final String sessionID;
    private final int width;
    private final int height;

    public JoinBundle(String sessionID, int width, int height) {
        this.sessionID = sessionID;
        this.width = width;
        this.height = height;
    }

    public String getSessionID() {
        return sessionID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
