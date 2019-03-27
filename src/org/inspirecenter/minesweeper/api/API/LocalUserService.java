package org.inspirecenter.minesweeper.api.API;

import org.inspirecenter.minesweeper.api.Exception.InvalidCellReferenceException;
import org.inspirecenter.minesweeper.api.Model.*;
import org.inspirecenter.minesweeper.api.Util.Storage;

public class LocalUserService implements UserService {

    @Override
    public PartialGameState getPartialState(String sessionID) {
        Session session = Storage.SESSIONS.get(sessionID);
        Game game = session.getGame();
        try {
            return new PartialGameState(session.getPartialStatePreference().getWidth(),session.getPartialStatePreference().getHeight(), session.getPositionX(), session.getPositionY(), game.getFullGameState());
        }
        catch (InvalidCellReferenceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PartialGameState move(String sessionID, Direction direction) {
        Session session = Storage.SESSIONS.get(sessionID);
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
    public PartialGameState reveal(String sessionID, int x, int y) {
        Session session = Storage.SESSIONS.get(sessionID);
        Game game = session.getGame();
        FullGameState state = game.getFullGameState();

        if (x >= state.getWidth() || y >= state.getHeight()) {
            return null;
        }

        int adjacentMines = game.getFullGameState().countAdjacentMines(x, y);
        game.getFullGameState().getCells()[x][y].setRevealState(RevealState.getRevealStateFromNumberOfAdjacentMines(adjacentMines));

        return getPartialState(sessionID);
    }

    @Override
    public PartialGameState revealAll(String sessionID) {
        Session session = Storage.SESSIONS.get(sessionID);
        Game game = session.getGame();
        FullGameState state = game.getFullGameState();

        for (int x = 0; x < state.getWidth(); x++) {
            for (int y = 0; y < state.getHeight(); y++) {
                if (state.getCells()[x][y].getRevealState() == RevealState.COVERED) {
                    int adjacentMines = state.countAdjacentMines(x, y);
                    state.getCells()[x][y].setRevealState(RevealState.getRevealStateFromNumberOfAdjacentMines(adjacentMines));
                }
            }
        }

        return getPartialState(sessionID);
    }

    @Override
    public PartialGameState flag(String sessionID, int x, int y) {
        Session session = Storage.SESSIONS.get(sessionID);
        Game game = session.getGame();
        FullGameState state = game.getFullGameState();

        if (x >= state.getWidth() || y >= state.getHeight()) {
            return null;
        }

        if (state.getCells()[x][y].getRevealState() == RevealState.COVERED) {
            state.getCells()[x][y].setRevealState(RevealState.FLAGGED);
            return getPartialState(sessionID);
        }
        else return null;

    }

}
