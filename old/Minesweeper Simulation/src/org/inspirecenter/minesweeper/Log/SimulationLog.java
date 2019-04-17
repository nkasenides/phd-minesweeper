package org.inspirecenter.minesweeper.Log;

import org.inspirecenter.minesweeper.Model.Player;
import org.inspirecenter.minesweeper.Model.Solver.MoveType;
import org.inspirecenter.minesweeper.Simulation;

import java.util.ArrayList;

public class SimulationLog {

    private ArrayList<SimulationLogEntry> entries;

    public SimulationLog() {
        entries = new ArrayList<>();
    }

    public ArrayList<SimulationLogEntry> getEntries() {
        return entries;
    }

    public boolean addPlayerMoveEntry(Player player, int x, int y, MoveType moveType) {
        return entries.add(new SimulationLogEntry(SimulationLogEntryType.INFO, "'" + player.getName() + "' performed move '" + moveType.getName() + "' on cell (" + x + "," + y + ")."));
    }

    public boolean addGameStartedEntry() {
        return entries.add(new SimulationLogEntry(SimulationLogEntryType.INFO, "Game started."));
    }

    public boolean addGameFinishedEntry() {
        return entries.add(new SimulationLogEntry(SimulationLogEntryType.INFO, "Game finished."));
    }

    public boolean addGameInitializedEntry() {
        return entries.add(new SimulationLogEntry(SimulationLogEntryType.INFO, "Game initialized."));
    }

    public boolean addPlayerLostEntry(Player player) {
        return entries.add(new SimulationLogEntry(SimulationLogEntryType.INFO, "Player '" + player.getName() + "' lost."));
    }

    public boolean addGameNotInitializedEntry() {
        return entries.add(new SimulationLogEntry(SimulationLogEntryType.ERROR, "Game not initialized. Aborting."));
    }

    public boolean addLogEntry(SimulationLogEntry entry) {
        return entries.add(entry);
    }

}
