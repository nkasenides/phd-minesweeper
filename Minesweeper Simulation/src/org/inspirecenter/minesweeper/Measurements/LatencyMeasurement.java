package org.inspirecenter.minesweeper.Measurements;

public class LatencyMeasurement {

    private long timestamp;
    private long latency;

    public LatencyMeasurement(long latency) {
        this.timestamp = System.currentTimeMillis();
        this.latency = latency;
    }

    public LatencyMeasurement() { this(0); }

    public long getTimestamp() {
        return timestamp;
    }

    public long getLatency() {
        return latency;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }
}
