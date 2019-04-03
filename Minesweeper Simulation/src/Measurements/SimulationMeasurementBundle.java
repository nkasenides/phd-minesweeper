package Measurements;

import java.util.ArrayList;

public class SimulationMeasurementBundle {

    private ArrayList<LatencyMeasurement> latencyMeasurements;

    public SimulationMeasurementBundle() {
        this.latencyMeasurements = new ArrayList<>();
    }

    public ArrayList<LatencyMeasurement> getLatencyMeasurements() {
        return latencyMeasurements;
    }

    public boolean addLatencyMeasurement(LatencyMeasurement latencyMeasurement) {
        return latencyMeasurements.add(latencyMeasurement);
    }

}
