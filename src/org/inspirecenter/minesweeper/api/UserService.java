package org.inspirecenter.minesweeper.api;

public interface UserService {

    public PartialState getPartialState(String sessionID);

    public PartialState move(String sessionID, Direction direction);

    public PartialState reveal(String sessionID);

    public PartialState revealAll(String sessionID);

    public PartialState flag(String sessionID);

}