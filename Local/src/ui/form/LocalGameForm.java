package ui.form;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import model.*;
import response.ResponseStatus;
import services.LocalMasterService;
import services.LocalUserService;
import ui.LocalMain;
import ui.component.MinesweeperButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LocalGameForm extends JFrame {

    //UI
    private JPanel gamePanel;
    private MinesweeperButton[][] buttons;
    private static final int WINDOW_SIZE = 500;

    //State
    private final String sessionID;
    private final PartialStatePreference partialStatePreference;
    private final int totalWidth;
    private final int totalHeight;
    private PartialBoardState localGameBoardState;
    private GameState localGameState;
    private int currentX = 0;
    private int currentY = 0;

    public LocalGameForm(String sessionID, int totalWidth, int totalHeight, PartialStatePreference partialStatePreference) {

        //Initialize UI:
        super("Minesweeper");
        this.sessionID = sessionID;
        this.partialStatePreference = partialStatePreference;
        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.buttons = new MinesweeperButton[partialStatePreference.getWidth()][partialStatePreference.getHeight()];
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(WINDOW_SIZE, WINDOW_SIZE);

        //Initialize state:
        retrieveGameState();
        initializeButtons();

        //Set key listener:
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
                            if (currentX + partialStatePreference.getWidth() < totalWidth) {
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
                            if (currentY + partialStatePreference.getHeight() < totalHeight) {
                                direction = Direction.RIGHT;
                                currentY++;
                            }
                            break;
                    }
                    System.out.println("cX: " + currentX + ", cY: " + currentY);
                    if (direction != null) {
                        move(direction, 1);
                        updateButtons(localGameState);
                    }
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
    }

    /**
     * Calls the userService/getPartialState endpoint.
     */
    private void retrieveGameState() {
        String json = LocalMain.userService.getPartialState(sessionID);
        System.out.println(json);
        Gson gson = new Gson();



//        try {
//
//            localGameBoardState = gson.fromJson(json, PartialBoardState.class);
//            updateButtons(); //TODO
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Could not retrieve partial state.");
//            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//        }
    }

    /**
     * Calls the userService/move endpoint.
     */
    private void move(Direction direction, int units) {
        if (units > 0) {
            String json = LocalMain.userService.move(sessionID, direction, units);
            Gson gson = new Gson();
            //TODO Deserialize JSON.
            updateButtons(localGameState);
        }
    }

    /**
     * Calls the userService/reveal endpoint.
     * @param x x-coordinate of cell to reveal.
     * @param y y-coordinate of cell to reveal.
     */
    private void reveal(int x, int y) {

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
                            if (localGameBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED) {
                                String json = LocalMain.userService.reveal(sessionID, innerX, innerY);

                                //TODO

                                updateButtons(localGameState);

//                                RevealBundle bundle = USER_SERVICE.reveal(OldMain.sessionID, innerX, innerY);
//                                if (bundle != null) {
//                                    localGameBoardState = bundle.getPartialGameState();
//                                    if (bundle.getGameState() == GameState.ENDED_LOST) {
//                                        RevealBundle bundleLost = USER_SERVICE.revealAll(OldMain.sessionID);
//                                        if (bundleLost != null) {
//                                            localGameBoardState = bundle.getPartialGameState();
//                                            updateButtons(bundleLost.getGameState());
//                                        }
//                                    }
//                                    else {
//                                        updateButtons(bundle.getGameState());
//                                    }
//                                }
                            }
                        }

                        //Right mouse button
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            String json = LocalMain.userService.flag(sessionID, innerX, innerY);

                            // TODO

                            updateButtons(localGameState);

//                            RevealBundle bundle = USER_SERVICE.flag(OldMain.sessionID, innerX, innerY);
//                            if (bundle != null) {
//                                localGameBoardState = bundle.getPartialGameState();
//                                updateButtons(bundle.getGameState());
//                            }
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
        for (int x = 0; x < localGameBoardState.getWidth(); x++) {
            for (int y = 0; y < localGameBoardState.getHeight(); y++) {

                if (gameState == GameState.STARTED) {
                    //Set default background:
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);

                    //Revealed buttons without any object are shown with grey background:
                    if (localGameBoardState.getCells()[x][y].getRevealState() == RevealState.REVEALED_0) {
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
                switch (localGameBoardState.getCells()[x][y].getRevealState()) {
                    case COVERED:
                        buttons[x][y].setIcon(null);
                        break;
                    case FLAGGED:
                        buttons[x][y].setIcon(MinesweeperButton.FLAG);
                        break;
                    case REVEALED_0:
                        buttons[x][y].setIcon(null);
                        break;
                    case REVEALED_1:
                        buttons[x][y].setIcon(MinesweeperButton.ONE);
                        break;
                    case REVEALED_2:
                        buttons[x][y].setIcon(MinesweeperButton.TWO);
                        break;
                    case REVEALED_3:
                        buttons[x][y].setIcon(MinesweeperButton.THREE);
                        break;
                    case REVEALED_4:
                        buttons[x][y].setIcon(MinesweeperButton.FOUR);
                        break;
                    case REVEALED_5:
                        buttons[x][y].setIcon(MinesweeperButton.FIVE);
                        break;
                    case REVEALED_6:
                        buttons[x][y].setIcon(MinesweeperButton.SIX);
                        break;
                    case REVEALED_7:
                        buttons[x][y].setIcon(MinesweeperButton.SEVEN);
                        break;
                    case REVEALED_8:
                        buttons[x][y].setIcon(MinesweeperButton.EIGHT);
                        break;
                    case REVEALED_MINE:
                        buttons[x][y].setIcon(MinesweeperButton.MINE);
                        break;
                }

            }
        }
    }

    public PartialStatePreference getPartialStatePreference() {
        return partialStatePreference;
    }

}