package org.inspirecenter.minesweeper.api.API;

import org.inspirecenter.minesweeper.api.Exception.InvalidGameSpecificationException;
import org.inspirecenter.minesweeper.api.Model.*;
import org.inspirecenter.minesweeper.api.Util.Storage;

import java.util.ArrayList;
import java.util.UUID;

public class LocalMasterService implements MasterService {

    @Override
    public String createGame(int numOfPlayers, int width, int height, Difficulty difficulty) {
        try {
            GameSpecification gameSpecification = new GameSpecification(numOfPlayers, width, height, difficulty);
            new Game(gameSpecification);
            return gameSpecification.getToken();
        }
        catch (InvalidGameSpecificationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Game> listGames() {
        return Storage.GAMES;
    }

    @Override
    public String join(String token, String playerName, PartialStatePreference partialStatePreference) {
        Game game = Game.findGameSpecification(token);
        int sessionsInGame = Storage.countSessionsPerGame(token);

        if (Storage.playerIsInGame(playerName, token)) {
            return null;
        }

        if (sessionsInGame < game.getGameSpecification().getMaxPlayers()) {
            Session session = new Session(partialStatePreference, playerName, game);
            UUID sessionID = UUID.randomUUID();
            Storage.SESSIONS.put(sessionID.toString(), session);
            return sessionID.toString();
        }
        else {
            return null;
        }
    }

}
