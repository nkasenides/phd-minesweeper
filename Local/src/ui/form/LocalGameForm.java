package ui.form;

import com.google.gson.*;
import model.*;
import response.Response;
import response.ResponseStatus;
import ui.LocalMain;
import ui.component.MinesweeperButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LocalGameForm extends ObserverForm {

    //UI
    private JPanel gamePanel;
    private MinesweeperButton[][] buttons;
    private static final int WINDOW_SIZE = 500;

    //State
//    private String sessionID;
//    private final PartialStatePreference partialStatePreference;
    private final int totalWidth;
    private final int totalHeight;
//    private PartialBoardState partialBoardState;
//    private GameState gameState;
//    private int xShift = 0;
//    private int yShift = 0;

    //Other
    private final Gson gson = new Gson();
    private LocalMainForm caller;

    public LocalGameForm(LocalMainForm form, int totalWidth, int totalHeight, PartialStatePreference partialStatePreference) {

        super(partialStatePreference);

        //Initialize UI:
        setTitle("Play | Minesweeper");
        this.caller = form;
        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.buttons = new MinesweeperButton[partialStatePreference.getWidth()][partialStatePreference.getHeight()];
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setLocationRelativeTo(null);

        //Set key listener:
        KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {

                if (isActive()) {
                    if (e.getID() == KeyEvent.KEY_PRESSED) {
                        Direction direction = null;
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP:
                                if (xShift - 1 >= 0) {
                                    direction = Direction.UP;
                                    xShift--;
                                }
                                break;
                            case KeyEvent.VK_DOWN:
                                if (xShift + partialStatePreference.getWidth() < totalWidth) {
                                    direction = Direction.DOWN;
                                    xShift++;
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                if (yShift - 1 >= 0) {
                                    direction = Direction.LEFT;
                                    yShift--;
                                }
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (yShift + partialStatePreference.getHeight() < totalHeight) {
                                    direction = Direction.RIGHT;
                                    yShift++;
                                }
                                break;
                        }
                        System.out.println("cX: " + xShift + ", cY: " + yShift);
                        if (direction != null) {
                            move(direction, 1);
                            update();
                        }
                    }
                }

                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

        //On close listener
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                caller.setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void initialize(String sessionID) {

        setSessionID(sessionID);
        System.out.println("STARTED FORM WITH SESSION ID --> " + sessionID);

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
                            if (gameState == GameState.STARTED) {
                                if (partialBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED) {
                                    reveal(innerX + xShift, innerY + yShift);
                                }
                            }
                        }

                        //Right mouse button
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            if (gameState == GameState.STARTED) {
                                if (partialBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED
                                || partialBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.FLAGGED) {
                                    flag(innerX + xShift, innerY + yShift);
                                }
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

        //Initialize state:
        if (retrieveGameState()) {
            update();
        }

        setVisible(true);
    }

    public void update() {
        switch (gameState) {
            case NOT_STARTED:
                JOptionPane.showMessageDialog(null, "Game not started", "Error", JOptionPane.WARNING_MESSAGE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
            case STARTED:
                updateButtons();
                break;
            case ENDED_WON:
                updateButtons();
                JOptionPane.showMessageDialog(null, "Congratulations! You won!", "Success", JOptionPane.WARNING_MESSAGE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
            case ENDED_LOST:
                updateButtons();
                JOptionPane.showMessageDialog(null, "You lost! :(", "Fail", JOptionPane.WARNING_MESSAGE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
        }
    }

    private void updateButtons() {
        for (int x = 0; x < partialBoardState.getWidth(); x++) {
            for (int y = 0; y < partialBoardState.getHeight(); y++) {

                //Set background:
                switch (gameState) {
                    case NOT_STARTED:
                        break;
                    case STARTED:
                        buttons[x][y].setBackground(Color.LIGHT_GRAY);
                        //Set the background of blank revealed cells to a darker gray:
                        if (partialBoardState.getCells()[x][y].getRevealState() == RevealState.REVEALED_0) {
                            buttons[x][y].setBackground(Color.GRAY);
                        }
                        break;
                    case ENDED_WON:
                        buttons[x][y].setBackground(Color.GREEN);
                        break;
                    case ENDED_LOST:
                        buttons[x][y].setBackground(Color.RED);
                        break;
                }

                //Set the icon:
                switch (partialBoardState.getCells()[x][y].getRevealState()) {
                    case COVERED:
                    case REVEALED_0:
                        buttons[x][y].setIcon(null);
                        break;
                    case FLAGGED:
                        buttons[x][y].setIcon(MinesweeperButton.FLAG);
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

    //Calls the userService/getPartialState endpoint.
    private boolean retrieveGameState() {
        String json = LocalMain.userService.getPartialState(sessionID);
        if (LocalMain.DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.getAsJsonObject(Response.DATA_TAG);
            gameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            partialBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    //Calls the userService/reveal endpoint.
    private void reveal(int x, int y) {
        String json = LocalMain.userService.reveal(sessionID, x, y);
        if (LocalMain.DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.get(Response.DATA_TAG).getAsJsonObject();
            gameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            partialBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
            update();
        }
        else {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        }
    }

    //Calls the userService/flag endpoint.
    private void flag(int x, int y) {
        String json = LocalMain.userService.flag(sessionID, x, y);
        if (LocalMain.DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.get(Response.DATA_TAG).getAsJsonObject();
            gameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            partialBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
            update();
        }
        else {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        }
    }

    //Calls the userService/move endpoint.
    private void move(Direction direction, int units) {
        if (units > 0) {
            String json = LocalMain.userService.move(sessionID, direction, units);
            if (LocalMain.DEBUG) System.out.println(json);

            JsonElement jsonElement = new JsonParser().parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

            if (status == ResponseStatus.OK) {
                JsonObject jsonObjectData = jsonObject.get(Response.DATA_TAG).getAsJsonObject();
                gameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
                partialBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
                update();
            }
            else {
                JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}