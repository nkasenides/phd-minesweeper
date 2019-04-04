package org.inspirecenter.minesweeper.Measurements;

public class LatencyMeasurement {

    private long timestamp;
    private long latency;

    public LatencyMeasurement(long timestamp, long latency) {
        this.timestamp = timestamp;
        this.latency = latency;
    }

    public LatencyMeasurement() { this(Integer.MIN_VALUE, Integer.MIN_VALUE); }

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
