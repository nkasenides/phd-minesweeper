package org.inspirecenter.minesweeper.api.UI;

import org.inspirecenter.minesweeper.api.Main;
import org.inspirecenter.minesweeper.api.Model.Direction;
import org.inspirecenter.minesweeper.api.Model.PartialGameState;
import org.inspirecenter.minesweeper.api.Model.PartialStatePreference;
import org.inspirecenter.minesweeper.api.Model.RevealState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameForm extends JFrame {

    private JPanel gamePanel;
    private final PartialStatePreference partialStatePreference;
    private PartialGameState state;
    private MinesweeperButton[][] buttons;

    private static final int WINDOW_SIZE = 500;

    private int currentX = 0;
    private int currentY = 0;

    public GameForm(PartialStatePreference partialStatePreference, PartialGameState state) {

        super("Minesweeper");
        this.partialStatePreference = partialStatePreference;
        this.state = state;
        this.buttons = new MinesweeperButton[state.getWidth()][state.getHeight()];

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        initializeButtons();

        KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override

            /**
             * case UP:
             *                 if (x > 0) session.setPositionX(x - 1);
             *                 break;
             *             case DOWN:
             *                 if (x < session.getGame().getGameSpecification().getWidth() - 1) session.setPositionX(x + 1);
             *                 break;
             *             case LEFT:
             *                 if (y > 0) session.setPositionY(y - 1);
             *                 break;
             *             case RIGHT:
             *                 if (y < session.getGame().getGameSpecification().getHeight() - 1) session.setPositionY(y + 1);
             *                 break;
             */

            public boolean dispatchKeyEvent(final KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    Direction direction = null;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (currentX > 0) {
                                direction = Direction.UP;
                                currentX--;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (currentX + partialStatePreference.getWidth() < Main.currentGameWidth) {
                                direction = Direction.DOWN;
                                currentX++;
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (currentY > 0) {
                                direction = Direction.LEFT;
                                currentY--;
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (currentY + partialStatePreference.getHeight() < Main.currentGameHeight) {
                                direction = Direction.RIGHT;
                                currentY++;
                            }
                            break;
                    }
                    System.out.println("cX: " + currentX + ", cY: " + currentY + ", w: " + Main.currentGameWidth + ", h: " + Main.currentGameHeight);
                    if (direction != null) {
                        PartialGameState partialGameState = Main.USER_SERVICE.move(Main.sessionID, direction);
                        if (partialGameState != null) {
                            GameForm.this.state = partialGameState;
                        }
                        updateButtons();
                    }
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
    }

    private void initializeButtons() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(partialStatePreference.getWidth(), partialStatePreference.getHeight()));
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
                gamePanel.add(button);
            }
        }
        add(gamePanel);
        setVisible(true);
    }

    private void updateButtons() {
        for (int x = 0; x < state.getWidth(); x++) {
            for (int y = 0; y < state.getHeight(); y++) {

                //Set default background:
                buttons[x][y].setBackground(Color.LIGHT_GRAY);

                //Revealed buttons without any object are shown with grey background:
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