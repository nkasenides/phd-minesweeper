package org.inspirecenter.minesweeper.Measurements;

import java.util.ArrayList;

public class SimulationMeasurementBundle {

    private ArrayList<LatencyMeasurement> latencyMeasurements;
    private ArrayList<MemoryMeasurement> memoryMeasurements;
    private int ticks = 0;

    public SimulationMeasurementBundle() {
        this.latencyMeasurements = new ArrayList<>();
        this.memoryMeasurements = new ArrayList<>();
    }

    public ArrayList<LatencyMeasurement> getLatencyMeasurements() {
        return latencyMeasurements;
    }

    public ArrayList<MemoryMeasurement> getMemoryMeasurements() {
        return memoryMeasurements;
    }

    public boolean addLatencyMeasurement(LatencyMeasurement latencyMeasurement) {
        return latencyMeasurements.add(latencyMeasurement);
    }

    public boolean addMemoryMeasurement(MemoryMeasurement memoryMeasurement) {
        return memoryMeasurements.add(memoryMeasurement);
    }

    public int getTicks() {
        return ticks;
    }

    public void resetTicks() {
        this.ticks = 0;
    }

    public void increaseTicks() {this.ticks++;}

}
