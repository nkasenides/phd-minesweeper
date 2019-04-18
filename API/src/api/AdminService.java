package api;

public interface AdminService {

    /**
     * Starts a game using its gameToken.
     * @param adminPassword Administrator password (admin access only).
     * @param gameToken The game's token.
     * @return Returns JSON formatted string with the status of the game.
     */
    String startGame(String adminPassword, String gameToken);

}
