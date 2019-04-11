package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Measurements.LatencyMeasurement;
import org.inspirecenter.minesweeper.Measurements.SimulationMeasurementBundle;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SimulationManager {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss:SS");
    private int simulationsRan = 0;

    private ArrayList<Simulation> simulations;

    SimulationManager() {
        this.simulations = new ArrayList<>();
    }

    public boolean addSimulation(Simulation simulation) {
        return simulations.add(simulation);
    }

    public int getSimulationsRan() {
        return simulationsRan;
    }

    public void runAll() {
        simulationsRan = 0;
        for (int i = 0; i < simulations.size(); i++) {
            try {
                System.out.print("Initializing simulation " + (i + 1) + "/" + simulations.size() + "...");
                simulations.get(i).initialize();
                System.out.println(" - DONE");
            }
            catch (InvalidGameSpecificationException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

            if (simulations.get(i).isInitialized()) {
                System.out.print("Running simulation " + (i + 1) + "/" + simulations.size() + "...");
                simulations.get(i).run();
                System.out.println(" - DONE");
                simulationsRan++;
            }
        }
    }

    public void runSingle(int index) {
        try {
            System.out.print("Initializing simulation ...");
            simulations.get(index).initialize();
            System.out.println(" - DONE");
        }
        catch (InvalidGameSpecificationException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        if (simulations.get(index).isInitialized()) {
            try {
                simulations.get(index).run();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Simulation> getSimulations() {
        return simulations;
    }

    private long _getRunTimeStarted() {
        long minTimeStarted = Long.MAX_VALUE;
        for (Simulation s : simulations) {
            if (s.getSimulationStats().getRunStartTime() < minTimeStarted) {
                minTimeStarted = s.getSimulationStats().getRunStartTime();
            }
        }
        return minTimeStarted;
    }

    private long _getRunTimeEnded() {
        long maxTimeEnded = 0;
        for (Simulation s : simulations) {
            if (s.getSimulationStats().getRunEndTime() > maxTimeEnded) {
                maxTimeEnded = s.getSimulationStats().getRunEndTime();
            }
        }
        return maxTimeEnded;
    }

    public String getTimeStarted() {
        return SIMPLE_DATE_FORMAT.format(new Date(_getRunTimeStarted()));
    }

    public String getTimeEnded() {
        return SIMPLE_DATE_FORMAT.format(new Date(_getRunTimeEnded()));
    }

    public String getRunTimeTaken() {
        long started = _getRunTimeStarted();
        long ended = _getRunTimeEnded();
        long timeTaken = ended - started;

        long minutes = (timeTaken / 1000) / 60;
        long seconds = (timeTaken / 1000) % 60;
        return String.format("%2dm %2ds (%d ms)", minutes, seconds, timeTaken);
    }

    public long getMinLatency() {
        long minLatency = Long.MAX_VALUE;
        if (simulations.size() < 1) return -1;
        for (Simulation s : simulations) {
            if (s.getMinLatency() < minLatency) {
                minLatency = s.getMinLatency();
            }
        }
        return minLatency;
    }

    public long getMaxLatency() {
        long maxLatency = 0;
        if (simulations.size() < 1) return -1;
        for (Simulation s : simulations) {
            if (s.getMaxLatency() > maxLatency) {
                maxLatency = s.getMaxLatency();
            }
        }
        return maxLatency;
    }

    public long getAverageLatency() {
        long latencySum = 0;
        for (Simulation s : simulations) {
            latencySum += s.getAverageLatency();
        }
        if (simulations.size() < 1) return 0;
        return latencySum / simulations.size();
    }

    public long _getInitializationTimeTaken() {
        long lowestInitStartTime = Long.MAX_VALUE;
        long largestInitEndTime = 0;
        for (Simulation s : simulations) {
            if (s.getSimulationStats().getInitStartTime() < lowestInitStartTime) {
                lowestInitStartTime = s.getSimulationStats().getInitStartTime();
            }
            if (s.getSimulationStats().getInitEndTime() > largestInitEndTime) {
                largestInitEndTime = s.getSimulationStats().getInitEndTime();
            }
        }
        return largestInitEndTime - lowestInitStartTime;
    }

    public String getInitializationTimeTaken() {
        long timeTaken = _getInitializationTimeTaken();
        long minutes = (timeTaken / 1000) / 60;
        long seconds = (timeTaken / 1000) % 60;
        return String.format("%2dm %2ds (%d ms)", minutes, seconds, timeTaken);
    }

    public static String formatAsSimpleDate(long timestamp) {
        return SIMPLE_DATE_FORMAT.format(timestamp);
    }

}
