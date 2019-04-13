package org.inspirecenter.minesweeper.API;

import org.inspirecenter.minesweeper.Model.Game;
import org.inspirecenter.minesweeper.Model.Position2D;
import org.inspirecenter.minesweeper.Model.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Emulates a back-end
public class Backend {

    public static final LocalUserService USER_SERVICE;
    public static final LocalMasterService MASTER_SERVICE;

    public static ArrayList<Game> GAMES; //Emulates DB storage (remove later)
    public static HashMap<String, Session> SESSIONS; //NK - Emulates DB storage for sessions (sessionID -> Session)

    static {
        USER_SERVICE = new LocalUserService();
        MASTER_SERVICE = new LocalMasterService();
        GAMES = new ArrayList<>();
        SESSIONS = new HashMap<>();
    }

    public static int countSessionsPerGame(String gameUUID) {
        int count = 0;
        for (Map.Entry<String, Session> entry : SESSIONS.entrySet()) {
            if (entry.getValue().getGame().getGameSpecification().getToken().equals(gameUUID)) count++;
        }
        return count;
    }

    public static boolean playerIsInGame(String playerName, String gameUUID) {
        for (Map.Entry<String, Session> entry : SESSIONS.entrySet()) {
            if (entry.getValue().getGame().getGameSpecification().getToken().equals(gameUUID) && entry.getValue().getPlayerName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    public static Position2D getPositionFromSession(String sessionID) {
        int x = SESSIONS.get(sessionID).getPositionX();
        int y = SESSIONS.get(sessionID).getPositionY();
        return new Position2D(x, y);
    }

}
