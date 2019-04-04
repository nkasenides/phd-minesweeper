package org.inspirecenter.minesweeper.API;

import org.inspirecenter.minesweeper.Model.*;
import org.inspirecenter.minesweeper.Model.Exception.InvalidCellReferenceException;

public class LocalUserService implements UserService {

    @Override
    public RevealBundle getPartialState(String sessionID) {
        Session session = Backend.SESSIONS.get(sessionID);
        Game game = session.getGame();
        try {
            PartialBoardState partialBoardState = new PartialBoardState(session.getPartialStatePreference().getWidth(),session.getPartialStatePreference().getHeight(), session.getPositionX(), session.getPositionY(), game.getFullBoardState());
            return new RevealBundle(partialBoardState, game.getGameState());
        }
        catch (InvalidCellReferenceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RevealBundle move(String sessionID, Direction direction) {
        Session session = Backend.SESSIONS.get(sessionID);
        final int x = session.getPositionX();
        final int y = session.getPositionY();
        switch (direction) {
            case UP:
                if (x > 0) session.setPositionX(x - 1);
                break;
            case DOWN:
                if (x < session.getGame().getGameSpecification().getWidth() - 1) session.setPositionX(x + 1);
                break;
            case LEFT:
                if (y > 0) session.setPositionY(y - 1);
                break;
            case RIGHT:
                if (y < session.getGame().getGameSpecification().getHeight() - 1) session.setPositionY(y + 1);
                break;
        }
        return getPartialState(sessionID);
    }

    @Override
    public RevealBundle reveal(String sessionID, int x, int y) {
        Session session = Backend.SESSIONS.get(sessionID);
        Game game = session.getGame();
        FullBoardState state = game.getFullBoardState();

        if (x >= state.getWidth() + session.getPositionX() || y >= state.getHeight() + session.getPositionY()) {
            return null;
        }

        if (game.getGameState() == GameState.NOT_STARTED) {
            game.setGameState(GameState.STARTED);
        }

        if (game.getGameState() != GameState.STARTED) {
            return null;
        }

        final int cX = x + session.getPositionX();
        final int cY = y + session.getPositionY();

        game.reveal(cX, cY);

        GameState gameState = game.computeGameState();
        game.setGameState(gameState);
        return getPartialState(sessionID);
    }

    @Override
    public RevealBundle revealAll(String sessionID) {
        Session session = Backend.SESSIONS.get(sessionID);
        Game game = session.getGame();

        if (game.getGameState() != GameState.ENDED_LOST) {
            return null;
        }

        FullBoardState state = game.getFullBoardState();

        for (int x = 0; x < state.getWidth(); x++) {
            for (int y = 0; y < state.getHeight(); y++) {
                if (state.getCells()[x][y].isMined()) {
                    state.getCells()[x][y].setRevealState(RevealState.REVEALED_MINE);
                }
                else {
                    int adjacentMines = game.getFullBoardState().countAdjacentMines(x + session.getPositionX(), y + session.getPositionY());
                    state.getCells()[x][y].setRevealState(RevealState.getRevealStateFromNumberOfAdjacentMines(adjacentMines));
                }
            }
        }

        return getPartialState(sessionID);
    }

    @Override
    public RevealBundle flag(String sessionID, int x, int y) {
        Session session = Backend.SESSIONS.get(sessionID);
        Game game = session.getGame();

        if (game.getGameState() == GameState.NOT_STARTED) {
            game.setGameState(GameState.STARTED);
        }

        if (game.getGameState() != GameState.STARTED) {
            return null;
        }

        FullBoardState state = game.getFullBoardState();

        if (x >= state.getWidth() || y >= state.getHeight()) {
            return null;
        }

        if (state.getCells()[x][y].getRevealState() == RevealState.COVERED) {
            state.getCells()[x][y].setRevealState(RevealState.FLAGGED);
        }
        else if (state.getCells()[x][y].getRevealState() == RevealState.FLAGGED) {
            state.getCells()[x][y].setRevealState(RevealState.COVERED);
        }

        game.setGameState(game.computeGameState());
        return getPartialState(sessionID);
    }

}
