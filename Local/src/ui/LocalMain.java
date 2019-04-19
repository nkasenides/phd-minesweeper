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

import javax.swing.*;
import java.util.ArrayList;

public class LocalMain {

    //API:
    public static final LocalMasterService masterService = new LocalMasterService();
    public static final LocalUserService userService = new LocalUserService();
    public static final LocalAdminService adminService = new LocalAdminService();
    public static final String ADMIN_PASSWORD = "1234";
    private static final Gson gson = new Gson();

    //Settings:
    public static final boolean DEBUG = true; //Switch true for debugging
    private static final PartialStatePreference partialStatePreference = new PartialStatePreference(5, 5);
    private static final String playerName = "Player1";
    private static ArrayList<GameSpecification> allGames; //All games (retrieved through the listGames() function).
    private static GameSpecification currentGame = null; //Game specification of the currently selected game (null if none).

    //Data:
    public static String sessionID;

    public static void main(String[] args) {

        //Create a 2 new games:
        String game1Token = createGame(ADMIN_PASSWORD, 10, 10, 10, Difficulty.EASY);
        System.out.println("Game created: " + game1Token);

        String game2Token = createGame(ADMIN_PASSWORD, 50, 10, 10, Difficulty.EASY);
        System.out.println("Game created: " + game2Token);

        //List the available games:
        System.out.println("List games:");
        allGames = listGames();
        if (allGames != null) {
            for (GameSpecification g : allGames) {
                System.out.println("[ " + g.getToken() + " ] (" + g.getWidth() + "," + g.getHeight() + ")");
            }
        }

        //Join the second game:
        System.out.println("Joining game '" + game2Token + "'...");
        if (join(game2Token, playerName, partialStatePreference)) {
            System.out.println("Joined game! --> " + sessionID);

            //Start the game:
            startGame(ADMIN_PASSWORD, game2Token);

        }

    }

    //Calls masterService/createGame
    public static String createGame(String password, int maxNumOfPlayers, int width, int height, Difficulty difficulty) {
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

    //Calls masterService/listGames
    public static ArrayList<GameSpecification> listGames() {
        String json = masterService.listGames();
        if (DEBUG) System.out.println(json);

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
    public static boolean join(String gameToken, String playerName, PartialStatePreference partialStatePreference) {
        String json = masterService.join(gameToken, playerName, partialStatePreference);
        if (DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.getAsJsonObject(Response.DATA_TAG);
            sessionID = jsonObjectData.get("sessionID").getAsString();
            for (GameSpecification gs : allGames) {
                if (gs.getToken().equals(gameToken)) {
                    currentGame = gs;
                    return true;
                }
            }
        }
        JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        return false;
    }

    //Calls adminService/startGame
    public static boolean startGame(String password, String gameToken) {
        String json = adminService.startGame(password, gameToken);
        if (DEBUG) System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.getAsJsonObject(Response.DATA_TAG);
            GameState gameState = GameState.valueOf(jsonObjectData.get("gameState").getAsString());
            LocalGameForm gameForm = new LocalGameForm(sessionID, currentGame.getWidth(), currentGame.getHeight(), partialStatePreference);
            gameForm.setLocalGameState(gameState);
            return true;
        }
        JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        return false;
    }

}
