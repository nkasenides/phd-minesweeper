package model.simulated;

import model.Game;
import model.PartialStatePreference;

public abstract class SimulatedPlayer {

    private final String name;
    private final String sessionID;
    private final PartialStatePreference partialStatePreference;

    public SimulatedPlayer(String name, String sessionID, PartialStatePreference partialStatePreference) {
        this.name = name;
        this.sessionID = sessionID;
        this.partialStatePreference = partialStatePreference;
    }

    public String getName() {
        return name;
    }

    public String getSessionID() {
        return sessionID;
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

    public abstract Move makeMove(Game game);

}
