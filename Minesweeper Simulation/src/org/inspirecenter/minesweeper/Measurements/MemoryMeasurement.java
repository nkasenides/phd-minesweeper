package org.inspirecenter.minesweeper.Measurements;

public class MemoryMeasurement {

    private long timestamp;
    private int memoryUsed;

    public MemoryMeasurement(long timestamp, int memoryUsed) {
        this.timestamp = timestamp;
        this.memoryUsed = memoryUsed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(int memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

}
