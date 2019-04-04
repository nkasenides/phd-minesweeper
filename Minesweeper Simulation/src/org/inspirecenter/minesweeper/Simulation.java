package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Measurements.LatencyMeasurement;
import org.inspirecenter.minesweeper.Measurements.SimulationMeasurementBundle;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;
import org.inspirecenter.minesweeper.Model.Game;
import org.inspirecenter.minesweeper.Model.GameSpecification;
import org.inspirecenter.minesweeper.Model.PartialStatePreference;

public abstract class Simulation {

    private final int totalWidth;
    private final int totalHeight;
    private final int partialWidth;
    private final int partialHeight;
    private final int numOfPlayers;
    private Game game;
    private final PartialStatePreference partialStatePreference;
    private boolean initialized = false;
    private SimulationStats simulationStats;

    public Simulation(int totalWidth, int totalHeight, int partialWidth, int partialHeight, int numOfPlayers) {
        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.partialWidth = partialWidth;
        this.partialHeight = partialHeight;
        this.numOfPlayers = numOfPlayers;
        partialStatePreference = new PartialStatePreference(partialWidth, partialHeight);
        simulationStats = new SimulationStats();
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

    public void initialize() throws InvalidGameSpecificationException {
        long startTime = System.currentTimeMillis();
        game = new Game(new GameSpecification(numOfPlayers, totalWidth, totalHeight));
        initializeGame();
        long endTime = System.currentTimeMillis();
        simulationStats.setInitStartTime(startTime);
        simulationStats.setInitEndTime(endTime);
        initialized = true;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        SimulationMeasurementBundle bundle = runGame();
        long endTime = System.currentTimeMillis();

        //Latency:
        if (bundle.getLatencyMeasurements().size() > 0) {
            LatencyMeasurement minLatencyMeasurement = new LatencyMeasurement();
            LatencyMeasurement maxLatencyMeasurement = new LatencyMeasurement();

            long minLatency = Long.MAX_VALUE;
            long maxLatency = 0;
            long latencySum = 0;
            for (LatencyMeasurement l : bundle.getLatencyMeasurements()) {
                if (l.getLatency() < minLatency) {
                    minLatency = l.getLatency();
                    minLatencyMeasurement = l;
                }
                if (l.getLatency() > maxLatency) {
                    maxLatency = l.getLatency();
                    maxLatencyMeasurement = l;
                }
                latencySum += l.getLatency();
            }

            simulationStats.setMaximumLatency(maxLatencyMeasurement);
            simulationStats.setMinimumLatency(minLatencyMeasurement);
            simulationStats.setAverageLatency(latencySum / bundle.getLatencyMeasurements().size());
        }

        //Add more measurement refinements here...

        //Timing:
        simulationStats.setRunEndTime(endTime);
        simulationStats.setRunStartTime(startTime);
    }

    public abstract void initializeGame();

    public abstract SimulationMeasurementBundle runGame();

}
