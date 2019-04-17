package org.inspirecenter.minesweeper.API;

import org.inspirecenter.minesweeper.Model.*;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;
import org.inspirecenter.minesweeper.Model.Solver.MinesweeperSolver;
import org.inspirecenter.minesweeper.Model.Solver.RandomMinesweeperSolver;

import java.util.ArrayList;
import java.util.Random;
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
    public JoinBundle join(String token, String playerName, String solverName, PartialStatePreference partialStatePreference) {
        Game game = Game.findGameSpecification(token);
        assert game != null;

        //TODO MORE SOLVERS
        MinesweeperSolver solver = null;
        if (solverName.toLowerCase().equals("random")) {
            solver = new RandomMinesweeperSolver(game, partialStatePreference);
        }

        if (solver == null) {
            return null; //Invalid solver
        }

        Player player = new Player(playerName, solver);
        int sessionsInGame = Backend.countSessionsPerGame(token);

        if (Backend.playerIsInGame(playerName, token)) {
            return null; //Player already exists in game
        }

        if (sessionsInGame < game.getGameSpecification().getMaxPlayers()) {
            Session session = new Session(partialStatePreference, playerName, game);
            UUID sessionID = UUID.randomUUID();
            Backend.SESSIONS.put(sessionID.toString(), session);
            if (game.addPlayer(player)) {
                return new JoinBundle(sessionID.toString(), game.getGameSpecification().getWidth(), game.getGameSpecification().getHeight());
            }
            else {
                return null; //Could not add the player to the game
            }
        }
        else {
            return null; //Max players reached
        }
    }

}
