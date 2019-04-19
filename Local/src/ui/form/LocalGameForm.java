package ui.form;

import com.google.gson.*;
import model.*;
import response.Response;
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

    //Other
    private final Gson gson = new Gson();

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
        initializeButtons();

        //Initialize state:
        if (retrieveGameState()) {
            update();
        }

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
                        update();
                    }
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
    }

    public void setLocalGameState(GameState localGameState) {
        this.localGameState = localGameState;
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
                            if (localGameState == GameState.STARTED) {
                                if (localGameBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED) {
                                    reveal(innerX, innerY);
                                }
                            }
                        }

                        //Right mouse button
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            if (localGameState == GameState.STARTED) {
                                if (localGameBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.COVERED
                                || localGameBoardState.getCells()[innerX][innerY].getRevealState() == RevealState.FLAGGED) {
                                    flag(innerX, innerY);
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
        setVisible(true);
    }

    private void update() {
        switch (localGameState) {
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
        for (int x = 0; x < localGameBoardState.getWidth(); x++) {
            for (int y = 0; y < localGameBoardState.getHeight(); y++) {

                //Set background:
                switch (localGameState) {
                    case NOT_STARTED:
                        break;
                    case STARTED:
                        buttons[x][y].setBackground(Color.LIGHT_GRAY);
                        //Set the background of blank revealed cells to a darker gray:
                        if (localGameBoardState.getCells()[x][y].getRevealState() == RevealState.REVEALED_0) {
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
                switch (localGameBoardState.getCells()[x][y].getRevealState()) {
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
            localGameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            localGameBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
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
            localGameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            localGameBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
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
            localGameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            localGameBoardState = gson.fromJson(jsonObjectData.get("partialBoardState"), PartialBoardState.class);
            update();
        }
        else {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        }
    }

    //Calls the userService/move endpoint.
    private boolean move(Direction direction, int units) {
        if (units > 0) {
            String json = LocalMain.userService.move(sessionID, direction, units);
            //TODO
        }
        return true;
    }

}