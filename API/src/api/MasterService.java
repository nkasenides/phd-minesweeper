package api;

import model.Difficulty;
import model.PartialStatePreference;

public interface MasterService {

    /**
     * Creates a new game using the specified parameters.
     * @param maxNumOfPlayers The maximum number of players allowed in the game.
     * @param width The total width of the board.
     * @param height The total height of the board.
     * @param difficulty The game's difficulty @see{@link Difficulty}.
     * @return Returns a JSON formatted string containing the created game's token as data.
     */
    String createGame(int maxNumOfPlayers, int width, int height, Difficulty difficulty);

    /**
     * Retrieves a list of all games.
     * @return Returns a JSON formatted string containing the created games' tokens.
     */
    String listGames();

    /**
     * Allows a player to join a specified game.
     * @param token The token of the game to join.
     * @param playerName The player's name.
     * @param partialStatePreference The partial state preference of the player.
     * @return Returns a JSON formatted string containing the game session's UUID.
     */
    String join(String token, String playerName, PartialStatePreference partialStatePreference);

}
