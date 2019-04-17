package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Measurements.LatencyMeasurement;

public class SimulationStats {

    private long runStartTime;
    private long runEndTime;
    private long initStartTime;
    private long initEndTime;

    public SimulationStats(long runStartTime, long runEndTime, long initStartTime, long initEndTime) {
        this.runStartTime = runStartTime;
        this.runEndTime = runEndTime;
        this.initStartTime = initStartTime;
        this.initEndTime = initEndTime;
    }

    public SimulationStats() {
        this(0, 0, 0, 0);
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

    public long getInitTotalTime() {
        return initEndTime - initStartTime;
    }

    public long getRunTotalTime() {
        return runEndTime - runStartTime;
    }

}
