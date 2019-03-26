package org.inspirecenter.minesweeper.api.API;

import org.inspirecenter.minesweeper.api.Model.Direction;
import org.inspirecenter.minesweeper.api.Model.PartialGameState;

public interface UserService {

    //Retrieves partial state from a specified game for a given player
    public PartialGameState getPartialState(String sessionID); // TODO - Also provide a game ID?

    //Performs a move (shift of state) for a given player at the given direction.
    public PartialGameState move(String sessionID, Direction direction);

    //Performs a reveal action for the specified player
    public PartialGameState reveal(String sessionID);

    //Performs a reveal all action for the specified player
    public PartialGameState revealAll(String sessionID);

    //Performs a flag action for the specified player
    public PartialGameState flag(String sessionID);

}