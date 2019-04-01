package org.inspirecenter.minesweeper.api.Model;

public enum GameState {

    NOT_STARTED("Not started"),
    STARTED("Started"),
    ENDED_WON("Ended (Won)"),
    ENDED_LOST("Ended (Lost)")

    ;

    private final String name;

    GameState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
