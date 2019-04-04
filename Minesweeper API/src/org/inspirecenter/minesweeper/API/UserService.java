package org.inspirecenter.minesweeper.API;

import org.inspirecenter.minesweeper.Model.Direction;

public interface UserService {

    //Retrieves partial state from a specified game for a given player
    public RevealBundle getPartialState(String sessionID);

    //Performs a move (shift of state) for a given player at the given direction.
    //Note: Assuming single unit of movement.
    public RevealBundle move(String sessionID, Direction direction);

    //Performs a reveal action for the specified player
    public RevealBundle reveal(String sessionID, int x, int y);

    //Performs a reveal all action for the specified player
    public RevealBundle revealAll(String sessionID);

    //Performs a flag action for the specified player
    public RevealBundle flag(String sessionID, int x, int y);

}