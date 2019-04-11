package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Log.SimulationLog;
import org.inspirecenter.minesweeper.Measurements.LatencyMeasurement;
import org.inspirecenter.minesweeper.Measurements.SimulationMeasurementBundle;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;
import org.inspirecenter.minesweeper.Model.Game;
import org.inspirecenter.minesweeper.Model.GameSpecification;
import org.inspirecenter.minesweeper.Model.PartialStatePreference;
import org.inspirecenter.minesweeper.Model.Player;
import org.inspirecenter.minesweeper.Model.Solver.RandomMinesweeperSolver;

public abstract class Simulation {

    protected final int totalWidth;
    protected final int totalHeight;
    protected final int partialWidth;
    protected final int partialHeight;
    protected final int numOfPlayers;
    protected Game game;
    protected final PartialStatePreference partialStatePreference;
    protected boolean initialized = false;
    protected SimulationStats simulationStats;
    protected SimulationLog log;
    protected SimulationMeasurementBundle measurementBundle;

    public Simulation(int totalWidth, int totalHeight, int partialWidth, int partialHeight, int numOfPlayers) {
        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.partialWidth = partialWidth;
        this.partialHeight = partialHeight;
        this.numOfPlayers = numOfPlayers;
        partialStatePreference = new PartialStatePreference(partialWidth, partialHeight);
        simulationStats = new SimulationStats();
        log = new SimulationLog();
        measurementBundle = new SimulationMeasurementBundle();
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public int getPartialWidth() {
        return partialWidth;
    }

    public int getPartialHeight() {
        return partialHeight;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void setSimulationStats(SimulationStats simulationStats) {
        this.simulationStats = simulationStats;
    }

    public SimulationStats getSimulationStats() {
        return simulationStats;
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

    public SimulationMeasurementBundle getMeasurementBundle() {
        return measurementBundle;
    }

    public SimulationLog getLog() {
        return log;
    }

    public long getMinLatency() {
        long minLatency = Long.MAX_VALUE;
        if (measurementBundle.getLatencyMeasurements().size() < 1) return -1;
        for (LatencyMeasurement lm : measurementBundle.getLatencyMeasurements()) {
            if (lm.getLatency() < minLatency) {
                minLatency = lm.getLatency();
            }
        }
        return minLatency;
    }

    public long getMaxLatency() {
        long maxLatency = 0;
        if (measurementBundle.getLatencyMeasurements().size() < 1) return -1;
        for (LatencyMeasurement lm : measurementBundle.getLatencyMeasurements()) {
            if (lm.getLatency() > maxLatency) {
                maxLatency = lm.getLatency();
            }
        }
        return maxLatency;
    }

    public long getAverageLatency() {
        long latencySum = 0;
        int latencyMeasurementCount = 0;
        for (LatencyMeasurement lm : measurementBundle.getLatencyMeasurements()) {
            latencySum += lm.getLatency();
            latencyMeasurementCount++;
        }
        if (latencyMeasurementCount == 0) return 0;
        return latencySum / latencyMeasurementCount;
    }

    public void initialize() throws InvalidGameSpecificationException {
        long startTime = System.currentTimeMillis();

        game = new Game(new GameSpecification(numOfPlayers, totalWidth, totalHeight)); //TODO DIFFICULTY VARIETY

        //Create and add players:
        for (int i = 0; i < numOfPlayers; i++) {
            boolean added = game.addPlayer(new Player("Player " + (i + 1), new RandomMinesweeperSolver(game, partialStatePreference))); //TODO SOLVER VARIETY
            if (!added) {
                System.out.println("ERROR - Could not add player!");
            }
        }

        initializeGame();
        long endTime = System.currentTimeMillis();
        simulationStats.setInitStartTime(startTime);
        simulationStats.setInitEndTime(endTime);
        initialized = true;
        log.addGameInitializedEntry();
    }

    public void run() {
        if (initialized) {
            log.addGameStartedEntry();
            long startTime = System.currentTimeMillis();
            runGame();
            long endTime = System.currentTimeMillis();

            simulationStats.setRunEndTime(endTime);
            simulationStats.setRunStartTime(startTime);
            log.addGameFinishedEntry();
        }
        else {
            log.addGameNotInitializedEntry();
        }
    }

    public abstract void initializeGame();

    public abstract void runGame();

}
