package org.inspirecenter.minesweeper.api.API;

import org.inspirecenter.minesweeper.api.Model.GameSpecification;

public interface MasterService {

    //Creates a new game
    public GameSpecification create(int numOfPlayers, int width, int height);

    //Allows a player to join a specified game using a provided token
    public String join(String token); // returns session ID

}
