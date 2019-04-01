package org.inspirecenter.minesweeper.api.UI;

import org.inspirecenter.minesweeper.api.API.RevealBundle;
import org.inspirecenter.minesweeper.api.Main;
import org.inspirecenter.minesweeper.api.Model.*;
import sun.rmi.log.LogOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameForm extends JFrame {

    private JPanel gamePanel;
    private final PartialStatePreference partialStatePreference;
    private PartialBoardState localGameState;
    private MinesweeperButton[][] buttons;

    private static final int WINDOW_SIZE = 500;

    private int currentX = 0;
    private int currentY = 0;

    public GameForm(PartialStatePreference partialStatePreference) {

        super("Minesweeper");
        this.partialStatePreference = partialStatePreference;
        this.buttons = new MinesweeperButton[partialStatePreference.getWidth()][partialStatePreference.getHeight()];

//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(WINDOW_SIZE, WINDOW_SIZE);

        localGameState = Main.USER_SERVICE.getPartialState(Main.sessionID).getPartialGameState();

        initializeButtons();

        KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override
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
                        RevealBundle bundle = Main.USER_SERVICE.move(Main.sessionID, direction);
                        if (bundle != null) {
                            GameForm.this.localGameState = bundle.getPartialGameState();
                            updateButtons(bundle.getGameState());
                        }
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
        for (int x = 0; x < partialStatePreference.getWidth(); x++) {
            for (int y = 0; y < partialStatePreference.getHeight(); y++) {
                MinesweeperButton button = new MinesweeperButton();
                final int innerX = x;
                final int innerY = y;

                button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                        //Left mouse button
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (localGameState.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED) {
                                RevealBundle bundle = Main.USER_SERVICE.reveal(Main.sessionID, innerX, innerY);
                                if (bundle != null) {
                                    localGameState = bundle.getPartialGameState();
                                    if (bundle.getGameState() == GameState.ENDED_LOST) {
                                        RevealBundle bundleLost = Main.USER_SERVICE.revealAll(Main.sessionID);
                                        if (bundleLost != null) {
                                            localGameState = bundle.getPartialGameState();
                                            updateButtons(bundleLost.getGameState());
                                        }
                                    }
                                    else {
                                        updateButtons(bundle.getGameState());
                                    }
                                }
                            }
                        }

                        //Right mouse button
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            RevealBundle bundle = Main.USER_SERVICE.flag(Main.sessionID, innerX, innerY);
                            if (bundle != null) {
                                localGameState = bundle.getPartialGameState();
                                updateButtons(bundle.getGameState());
                            }
                        }

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

                buttons[x][y] = button;
                gamePanel.add(button);
            }
        }
        add(gamePanel);
        setVisible(true);
    }

    private void updateButtons(GameState gameState) {
        for (int x = 0; x < localGameState.getWidth(); x++) {
            for (int y = 0; y < localGameState.getHeight(); y++) {

                if (gameState == GameState.STARTED) {
                    //Set default background:
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);

                    //Revealed buttons without any object are shown with grey background:
                    if (localGameState.getCells()[x][y].getRevealState() == RevealState.REVEALED_0) {
                        buttons[x][y].setBackground(Color.GRAY);
                    }
                }
                else if (gameState == GameState.ENDED_WON) {
                    buttons[x][y].setBackground(Color.GREEN);
                }
                else if (gameState == GameState.ENDED_LOST) {
                    buttons[x][y].setBackground(Color.RED);
                }

                //Set the icon:
                buttons[x][y].setIcon(MinesweeperButton.getIconFromState(localGameState.getCells()[x][y]));

            }
        }
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

}