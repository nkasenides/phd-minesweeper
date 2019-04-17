package datastore;

import model.*;
import model.exception.InvalidGameSpecificationException;

import java.util.*;

public class Datastore {

    private static HashMap<String, Game> games = new HashMap<>(); //  Game token -> Game object
    private static HashMap<String, Session> sessions = new HashMap<>();   // Session ID -> Session object

    public static Session getSession(String sessionID) {
        return sessions.get(sessionID);
    }

    public static Game getGame(String gameID) {
        return games.get(gameID);
    }

    public static ArrayList<String> getGames() {
        ArrayList<String> gamesList = new ArrayList<>();
        Iterator it = games.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            gamesList.add((String) pair.getKey());
            it.remove();
        }
        return gamesList;
    }

    public static ArrayList<String> getSessions() {
        ArrayList<String> sessionsList = new ArrayList<>();
        Iterator it = sessions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            sessionsList.add((String) pair.getKey());
            it.remove();
        }
        return sessionsList;
    }

    public static String addGame(int maxPlayers, int width, int height, Difficulty difficulty) {
        try {
            GameSpecification gameSpecification = new GameSpecification(maxPlayers, width, height, difficulty);
            Game game = new Game(gameSpecification);
            String gameID = UUID.randomUUID().toString();
            games.put(gameID, game);
            return gameID;
        } catch (InvalidGameSpecificationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String addSession(String gameID, String playerName, PartialStatePreference partialStatePreference) {
        Game referencedGame = games.get(gameID);
        if (referencedGame == null) return null;
        Session session = new Session(partialStatePreference, playerName, gameID);
        sessions.put(session.getSessionID(), session);
        return session.getSessionID();
    }

    public static boolean removeSession(String sessionID) {
        return sessions.remove(sessionID) != null;
    }

    public static boolean removeGame(String gameID) {
        return games.remove(gameID) != null;
    }

}
