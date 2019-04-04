package org.inspirecenter.minesweeper.Model;

public enum UserState {

    ALIVE("Alive"), // user is alive and ready to play
    DEAD("Dead")// user already lost

    ;

    private final String name;

    UserState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
