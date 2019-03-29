package org.inspirecenter.minesweeper.api.UI;

import org.inspirecenter.minesweeper.api.Main;
import org.inspirecenter.minesweeper.api.Model.PartialGameState;
import org.inspirecenter.minesweeper.api.Model.PartialStatePreference;
import org.inspirecenter.minesweeper.api.Model.RevealState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameForm extends JFrame {

    private JPanel p;
    private final PartialStatePreference partialStatePreference;
    private PartialGameState state;
    private MinesweeperButton[][] buttons;

    public GameForm(PartialStatePreference partialStatePreference, PartialGameState state) {

        super("Minesweeper");
        this.partialStatePreference = partialStatePreference;
        this.state = state;
        this.buttons = new MinesweeperButton[state.getWidth()][state.getHeight()];

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(800, 600);
        initializeButtons();

    }

    private void initializeButtons() {
        p = new JPanel();
        p.setLayout(new GridLayout(partialStatePreference.getWidth(), partialStatePreference.getHeight()));
        for (int x = 0; x < state.getWidth(); x++) {
            for (int y = 0; y < state.getHeight(); y++) {
                MinesweeperButton button = new MinesweeperButton();
                final int innerX = x;
                final int innerY = y;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (state.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED) {
                            state = Main.USER_SERVICE.reveal(Main.sessionID, innerX, innerY);
                            updateButtons();
                        }
                    }
                });
                buttons[x][y] = button;
                p.add(button);
            }
        }
        add(p);
        setVisible(true);
    }

    private void updateButtons() {
        for (int x = 0; x < state.getWidth(); x++) {
            for (int y = 0; y < state.getHeight(); y++) {

                //Disable any buttons that are already revealed:
                if (state.getCells()[x][y].getRevealState() == RevealState.REVEALED_0) {
                    buttons[x][y].setBackground(Color.GRAY);
                }

                //Set the icon:
                buttons[x][y].setIcon(MinesweeperButton.getIconFromState(state.getCells()[x][y]));

            }
        }
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }
}