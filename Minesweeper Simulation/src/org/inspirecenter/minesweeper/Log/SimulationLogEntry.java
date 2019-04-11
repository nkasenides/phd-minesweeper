package org.inspirecenter.minesweeper.Log;

public class SimulationLogEntry {

    private final long timestamp;
    private final String text;
    private final SimulationLogEntryType type;

    public SimulationLogEntry(SimulationLogEntryType type, String text) {
        this.timestamp = System.currentTimeMillis();
        this.text = text;
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public SimulationLogEntryType getType() {
        return type;
    }

}
