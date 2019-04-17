package model;

public enum UserState {

    ALIVE("Alive"), //User is alive and ready to play
    DEAD("Dead") //User already lost

    ;

    private final String name;

    UserState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
