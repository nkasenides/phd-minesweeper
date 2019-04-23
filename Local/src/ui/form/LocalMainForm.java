package ui.form;

import com.google.gson.*;
import model.Difficulty;
import model.GameSpecification;
import model.GameState;
import model.PartialStatePreference;
import response.Response;
import response.ResponseStatus;
import services.LocalMasterService;
import ui.LocalMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class LocalMainForm extends JFrame {

    //API:
    public static final LocalMasterService masterService = new LocalMasterService();
    private static final Gson gson = new Gson();

    //UI:
    private JList gamesList;
    private JButton playButton;
    private JPanel mainPanel;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JTextField playerNameTextfield;

    //Data:
    private ArrayList<GameSpecification> allGames; //All games (retrieved through the listGames() function).

    public LocalMainForm() {

        //Initialize UI:
        super("Select game | Minesweeper");
        setSize(800, 500);
        setContentPane(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Set spinner model minimums:
        SpinnerNumberModel widthSpinnerModel = new SpinnerNumberModel(5, 5, 100, 5);
        SpinnerNumberModel heightSpinnerModel = new SpinnerNumberModel(5, 5, 100, 5);
        widthSpinner.setModel(widthSpinnerModel);
        heightSpinner.setModel(heightSpinnerModel);

        //Get all available games:
        allGames = listGames();

        //Show the games on the list:
        if (allGames != null) {
            DefaultListModel listModel = new DefaultListModel();
            for (GameSpecification s : allGames) {
                String name = s.getToken() + "[" + s.getWidth() + "," + s.getHeight() + "] (" + s.getDifficulty() + ")";
                listModel.addElement(name);
            }
            gamesList.setModel(listModel);
        }

        //Play button listener:
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedWidth = (int) widthSpinner.getValue();
                int selectedHeight = (int) widthSpinner.getValue();

                if (gamesList.getSelectedIndex() >= 0) {
                    GameSpecification selectedGame = allGames.get(gamesList.getSelectedIndex());
                    if (checkGameAndStatePreference(selectedGame, selectedWidth, selectedHeight)) {
                        PartialStatePreference partialStatePreference = new PartialStatePreference(selectedWidth, selectedHeight);
                        if (join(selectedGame.getToken(), playerNameTextfield.getText(), partialStatePreference)) {
                            LocalGameForm gameForm = new LocalGameForm(LocalMainForm.this, LocalMain.sessionID, selectedGame.getWidth(), selectedGame.getHeight(), partialStatePreference);
                            setVisible(false);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null,
                                "Cannot start a game with a state preference of " +
                                        widthSpinner.getValue() + "x" + heightSpinner.getValue() +
                                        ". The game's size is " + selectedGame.getWidth() + "x" + selectedGame.getHeight(),
                                "Invalid state preference", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please select a game first", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

    }

    private static boolean checkGameAndStatePreference(GameSpecification gameSpecification, int preferredWidth, int preferredHeight) {
        return preferredWidth <= gameSpecification.getWidth() && preferredHeight <= gameSpecification.getHeight();
    }

    //Calls masterService/listGames
    public ArrayList<GameSpecification> listGames() {
        String json = masterService.listGames();
        if (LocalMain.DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            try {
                JsonObject jsonObjectData = jsonObject.get(Response.DATA_TAG).getAsJsonObject();
                ArrayList<GameSpecification> gameSpecifications = new ArrayList<>();
                JsonArray gamesArray = jsonObjectData.getAsJsonArray("games");
                for (JsonElement arrayElement : gamesArray) {
                    JsonObject arrayObject = arrayElement.getAsJsonObject();
                    String token  = arrayObject.get("token").getAsString();
                    int width = arrayObject.get("width").getAsInt();
                    int height = arrayObject.get("height").getAsInt();
                    int maxPlayers = arrayObject.get("maxPlayers").getAsInt();
                    Difficulty difficulty = Difficulty.valueOf(arrayObject.get("difficulty").getAsString());
                    gameSpecifications.add(new GameSpecification(token, maxPlayers, width, height, difficulty));
                }
                return gameSpecifications;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        return null;
    }

    //Calls masterService/join
    public boolean join(String gameToken, String playerName, PartialStatePreference partialStatePreference) {
        String json = masterService.join(gameToken, playerName, partialStatePreference);
        if (LocalMain.DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.getAsJsonObject(Response.DATA_TAG);
            LocalMain.sessionID = jsonObjectData.get("sessionID").getAsString();
            for (GameSpecification gs : allGames) {
                if (gs.getToken().equals(gameToken)) {
                    LocalMain.currentGame = gs;
                    return true;
                }
            }
            return false;
        }
        else {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

}
