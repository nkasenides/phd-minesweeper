package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Log.SimulationLogEntry;
import org.inspirecenter.minesweeper.Log.SimulationLogEntryType;
import org.inspirecenter.minesweeper.Measurements.LatencyMeasurement;
import org.inspirecenter.minesweeper.Measurements.SimulationMeasurementBundle;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;
import org.inspirecenter.minesweeper.Model.GameState;
import org.inspirecenter.minesweeper.Model.Player;
import org.inspirecenter.minesweeper.Model.Solver.MinesweeperSolver;
import org.inspirecenter.minesweeper.Model.Solver.Move;
import org.inspirecenter.minesweeper.Model.Solver.MoveType;

public class LocalSimulation extends Simulation {

    public LocalSimulation(int totalWidth, int totalHeight, int partialWidth, int partialHeight, int numOfPlayers) throws InvalidGameSpecificationException {
        super(totalWidth, totalHeight, partialWidth, partialHeight, numOfPlayers);
    }

    @Override
    public void initializeGame() {
        game.start();
    }

    @Override
    public void runGame() {

        int moves = 0;

        while (!game.getGameState().isEnded() && moves < 1000) {

            long timeBefore = System.currentTimeMillis();

            if (game.getPlayers().size() > 0) {
                //Perform player moves and log them, one at a time:
                for (Player player : getGame().getPlayersAsList()) {
                    Move nextMove = player.getMinesweeperSolver().solve();
                    switch (nextMove.getMoveType()) {
                        case REVEAL:
                            game.reveal(nextMove.getX(), nextMove.getY());
                            log.addPlayerMoveEntry(player, nextMove.getX(), nextMove.getY(), MoveType.REVEAL);
                            break;
                        case FLAG:
                            game.flag(nextMove.getX(), nextMove.getY());
                            log.addPlayerMoveEntry(player, nextMove.getX(), nextMove.getY(), MoveType.FLAG);
                            break;
                        case NONE:
                            log.addPlayerMoveEntry(player, nextMove.getX(), nextMove.getY(), MoveType.NONE);
                            break;
                    }
                    moves++;
                }
            }
            else {
                game.setGameState(GameState.ENDED_LOST);
            }

            long timeAfter = System.currentTimeMillis();

            measurementBundle.addLatencyMeasurement(new LatencyMeasurement(timeAfter - timeBefore));
            measurementBundle.increaseTicks();
        }

    }

}
