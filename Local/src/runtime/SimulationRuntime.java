package runtime;

import datastore.Datastore;
import model.Game;
import model.GameState;
import model.simulated.Move;
import model.simulated.MoveType;
import model.simulated.SimulatedPlayer;

public class SimulationRuntime {

    public static boolean DEBUG = true;

    public static boolean makePlayerMove(String gameToken, SimulatedPlayer player) {
        Game game = Datastore.getGame(gameToken);
        Move playerMove = player.makeMove(game);
        if (playerMove.getMoveType() == MoveType.REVEAL) {
            game.reveal(playerMove.getX(), playerMove.getY());
        }
        else if (playerMove.getMoveType() == MoveType.FLAG) {
            game.flag(playerMove.getX(), playerMove.getY());
        }

        game.updateObservers();

        if (DEBUG) {
            System.out.println("Player '" + player.getName() + "' made move '" + playerMove.getMoveType() + "' on cell (" + playerMove.getX() + "," + playerMove.getY() + "). [Game state: " + game.getGameState() + "]");
        }

        return game.getGameState().isEnded();
    }

    public static boolean isGameOver(String gameToken) {
        Game game = Datastore.getGame(gameToken);
        return game.getGameState().isEnded();
    }

}
