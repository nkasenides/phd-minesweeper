package org.inspirecenter.minesweeper.Model.Solver;

public enum MoveType {

    REVEAL("Reveal"),
    FLAG("Flag"),
    NONE("None")

    ;

    private final String name;

    MoveType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
