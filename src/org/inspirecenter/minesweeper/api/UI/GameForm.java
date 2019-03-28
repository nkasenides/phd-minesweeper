package org.inspirecenter.minesweeper.api.UI;

import org.inspirecenter.minesweeper.api.Main;
import org.inspirecenter.minesweeper.api.Model.PartialGameState;
import org.inspirecenter.minesweeper.api.Model.PartialStatePreference;
import org.inspirecenter.minesweeper.api.Model.RevealState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameForm extends JFrame {

    private JPanel p;
    private final PartialStatePreference partialStatePreference;
    private PartialGameState state;
    private ArrayList<JButton> buttons;

    public GameForm(PartialStatePreference partialStatePreference, PartialGameState state) {

        super("Minesweeper");
        this.partialStatePreference = partialStatePreference;
        this.state = state;
        this.buttons = new ArrayList<>();

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(800, 600);
        updateButtons();

    }

    private void updateButtons() {
        p = new JPanel();
        p.setLayout(new GridLayout(partialStatePreference.getWidth(), partialStatePreference.getHeight()));
        for (int x = 0; x < state.getWidth(); x++) {
            for (int y = 0; y < state.getHeight(); y++) {
                MinesweeperButton button = new MinesweeperButton(state.getCells()[x][y]);
                if (state.getCells()[x][y].getRevealState() != RevealState.COVERED) button.setEnabled(false);
                final int innerX = x;
                final int innerY = y;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.USER_SERVICE.reveal(Main.sessionID, innerX, innerY);
                        System.out.println("X: " + innerX + ", Y: " + innerY);
                        updateButtons();
                    }
                });
                buttons.add(button);
                p.add(button);
            }
        }
        add(p);
        setVisible(true);
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }
}