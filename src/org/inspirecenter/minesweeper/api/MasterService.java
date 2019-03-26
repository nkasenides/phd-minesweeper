package org.inspirecenter.minesweeper.api;

public interface MasterService {

    public Game create(int numOfPlayers, int width, int height);

    public String join(String token); // returns session ID
}
