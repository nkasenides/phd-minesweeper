package org.inspirecenter.minesweeper.api.Util;

import org.inspirecenter.minesweeper.api.Model.Game;
import org.inspirecenter.minesweeper.api.Model.GameSpecification;
import org.inspirecenter.minesweeper.api.Model.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Emulates DB storage
public class Storage {

    public static ArrayList<Game> GAMES; //Emulates DB storage (remove later)
    public static HashMap<String, Session> SESSIONS; //NK - Emulates DB storage for sessions (sessionID -> Session)

    static {
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

}
