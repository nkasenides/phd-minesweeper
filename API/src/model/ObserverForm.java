package model;

import javax.swing.*;

/**
 * An observer object.
 */
public abstract class ObserverForm extends JFrame {

    protected final PartialStatePreference partialStatePreference;
    protected PartialBoardState partialBoardState;
    protected GameState gameState;
    protected int xShift = 0;
    protected int yShift = 0;
    protected String sessionID;

    public ObserverForm(PartialStatePreference partialStatePreference) {
        this.partialStatePreference = partialStatePreference;
    }

    public void update(PartialBoardState partialBoardState, GameState gameState) {
        this.partialBoardState = partialBoardState;
        this.gameState = gameState;
        update();
        System.out.println("updateButtons()");
    }

    public PartialBoardState getPartialBoardState() {
        return partialBoardState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

    public int getxShift() {
        return xShift;
    }

    public int getyShift() {
        return yShift;
    }

    public void setxShift(int xShift) {
        this.xShift = xShift;
    }

    public void setyShift(int yShift) {
        this.yShift = yShift;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public abstract void update();

}
