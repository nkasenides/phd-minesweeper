package org.inspirecenter.minesweeper.Log;

public enum SimulationLogEntryType {

    INFO("Info"),
    WARNING("Warning"),
    ERROR("Error")

    ;

    private final String name;

    SimulationLogEntryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
