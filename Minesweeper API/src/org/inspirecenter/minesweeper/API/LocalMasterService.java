package org.inspirecenter.minesweeper.API;

import org.inspirecenter.minesweeper.Model.*;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;

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
        return Backend.GAMES;
    }

    @Override
    public JoinBundle join(String token, String playerName, PartialStatePreference partialStatePreference) {
        Game game = Game.findGameSpecification(token);

        assert game != null;

        int sessionsInGame = Backend.countSessionsPerGame(token);

        if (Backend.playerIsInGame(playerName, token)) {
            return null;
        }

        if (sessionsInGame < game.getGameSpecification().getMaxPlayers()) {
            Session session = new Session(partialStatePreference, playerName, game);
            UUID sessionID = UUID.randomUUID();
            Backend.SESSIONS.put(sessionID.toString(), session);
            return new JoinBundle(sessionID.toString(), game.getGameSpecification().getWidth(), game.getGameSpecification().getHeight());
        }
        else {
            return null;
        }
    }

}
