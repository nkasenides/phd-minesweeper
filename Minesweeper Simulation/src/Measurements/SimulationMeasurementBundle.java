package Measurements;

import java.util.ArrayList;

public class SimulationMeasurementBundle {

    private ArrayList<LatencyMeasurement> latencyMeasurements;
    private int ticks = 0;

    public SimulationMeasurementBundle() {
        this.latencyMeasurements = new ArrayList<>();
    }

    public ArrayList<LatencyMeasurement> getLatencyMeasurements() {
        return latencyMeasurements;
    }

    public boolean addLatencyMeasurement(LatencyMeasurement latencyMeasurement) {
        return latencyMeasurements.add(latencyMeasurement);
    }

    public int getTicks() {
        return ticks;
    }

    public void resetTicks() {
        this.ticks = 0;
    }

    public void increaseTicks() {this.ticks++;}

}
