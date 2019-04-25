package ui;

import com.google.gson.*;
import model.Difficulty;
import model.GameSpecification;
import model.GameState;
import model.PartialStatePreference;
import response.Response;
import response.ResponseStatus;
import services.LocalAdminService;
import services.LocalMasterService;
import services.LocalUserService;
import ui.form.LocalGameForm;
import ui.form.LocalMainForm;

import javax.swing.*;
import java.util.ArrayList;

public class LocalMain {

    //API:
    public static final LocalUserService userService = new LocalUserService();
    public static final LocalAdminService adminService = new LocalAdminService();
    public static final String ADMIN_PASSWORD = "1234";

    //Settings:
    public static final boolean DEBUG = false; //Switch true for debugging
    public static GameSpecification currentGame = null; //Game specification of the currently selected game (null if none).

    public static void main(String[] args) {

        //Create a 2 new games:
        String game1Token = createGame(ADMIN_PASSWORD, 10, 10, 10, Difficulty.EASY);
        System.out.println("Game created: " + game1Token);

        String game2Token = createGame(ADMIN_PASSWORD, 50, 10, 10, Difficulty.EASY);
        System.out.println("Game created: " + game2Token);

        //Start the games:
        startGame(ADMIN_PASSWORD, game1Token);
        startGame(ADMIN_PASSWORD, game2Token);

        //Show the game selection form:
        LocalMainForm localMainForm1 = new LocalMainForm();
        LocalMainForm localMainForm2 = new LocalMainForm();

    }

    //Calls masterService/createGame
    private static String createGame(String password, int maxNumOfPlayers, int width, int height, Difficulty difficulty) {
        String json = adminService.createGame(password, maxNumOfPlayers, width, height, difficulty);
        if (DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.get(Response.DATA_TAG).getAsJsonObject();
            return jsonObjectData.get("gameToken").getAsString();
        }
        JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        return null;
    }

    //Calls adminService/startGame
    private static void startGame(String password, String gameToken) {
        String json = adminService.startGame(password, gameToken);
        if (DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status != ResponseStatus.OK) {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        }
    }

}
