package org.inspirecenter.minesweeper.api.API;

import org.inspirecenter.minesweeper.api.Model.Difficulty;
import org.inspirecenter.minesweeper.api.Model.Game;
import org.inspirecenter.minesweeper.api.Model.PartialStatePreference;

import java.util.ArrayList;

public interface MasterService {

    //Creates a new game
    public String createGame(int numOfPlayers, int width, int height, Difficulty difficulty);

    //NK - Lists all available game specifications
    public ArrayList<Game> listGames();

    //Allows a player to join a specified game using a provided token
    public String join(String token, String playerName, PartialStatePreference partialStatePreference); // returns session ID

}
