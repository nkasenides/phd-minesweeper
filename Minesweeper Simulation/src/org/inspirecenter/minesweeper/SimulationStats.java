package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Measurements.LatencyMeasurement;

public class SimulationStats {

    private long runStartTime;
    private long runEndTime;
    private long initStartTime;
    private long initEndTime;
    private LatencyMeasurement maximumLatency;
    private LatencyMeasurement minimumLatency;
    private long averageLatency;

    public SimulationStats(long runStartTime, long runEndTime, LatencyMeasurement maximumLatency, LatencyMeasurement minimumLatency, long averageLatency, long initStartTime, long initEndTime) {
        this.runStartTime = runStartTime;
        this.runEndTime = runEndTime;
        this.maximumLatency = maximumLatency;
        this.minimumLatency = minimumLatency;
        this.averageLatency = averageLatency;
        this.initStartTime = initStartTime;
        this.initEndTime = initEndTime;
    }

    public SimulationStats() {
        this(0, 0, new LatencyMeasurement(0, 0), new LatencyMeasurement(0, 0), 0, 0, 0);
    }

    public long getRunStartTime() {
        return runStartTime;
    }

    public void setRunStartTime(long runStartTime) {
        this.runStartTime = runStartTime;
    }

    public long getRunEndTime() {
        return runEndTime;
    }

    public void setRunEndTime(long runEndTime) {
        this.runEndTime = runEndTime;
    }

    public LatencyMeasurement getMaximumLatency() {
        return maximumLatency;
    }

    public void setMaximumLatency(LatencyMeasurement maximumLatency) {
        this.maximumLatency = maximumLatency;
    }

    public LatencyMeasurement getMinimumLatency() {
        return minimumLatency;
    }

    public void setMinimumLatency(LatencyMeasurement minimumLatency) {
        this.minimumLatency = minimumLatency;
    }

    public long getAverageLatency() {
        return averageLatency;
    }

    public void setAverageLatency(long averageLatency) {
        this.averageLatency = averageLatency;
    }

    public long getTimeTaken() {
        return runEndTime - runStartTime;
    }

    public long getInitStartTime() {
        return initStartTime;
    }

    public void setInitStartTime(long setupStartTime) {
        this.initStartTime = setupStartTime;
    }

    public long getInitEndTime() {
        return initEndTime;
    }

    public void setInitEndTime(long initEndTime) {
        this.initEndTime = initEndTime;
    }

}
