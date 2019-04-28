package model.simulated;

import model.CellState;
import model.Game;
import model.PartialStatePreference;

import java.util.Random;

public class DumbSimulatedPlayer extends SimulatedPlayer {

    public DumbSimulatedPlayer(String name, String sessionID, PartialStatePreference partialStatePreference) {
        super(name, sessionID, partialStatePreference);
    }

    @Override
    public Move makeMove(Game game) {
        Random random = new Random();
        int x = random.nextInt(game.getGameSpecification().getWidth());
        int y = random.nextInt(game.getGameSpecification().getHeight());

        int moveTypeInt = random.nextInt(2);
        MoveType moveType;
        if (moveTypeInt == 1) {
            moveType = MoveType.REVEAL;
        }
        else {
            moveType = MoveType.FLAG;
        }

        //Introduce some delay so that we can catch up to the moves:
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Move(moveType, x, y);
    }

}
