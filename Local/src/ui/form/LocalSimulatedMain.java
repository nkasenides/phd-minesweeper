package ui.form;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.*;
import model.simulated.DumbSimulatedPlayer;
import model.simulated.SimulatedPlayer;
import response.Response;
import response.ResponseStatus;
import runtime.SimulationRuntime;
import services.LocalAdminService;
import services.LocalMasterService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LocalSimulatedMain {

    //API:
    public static final LocalMasterService masterService = new LocalMasterService();
    public static final LocalAdminService adminService = new LocalAdminService();
    public static final String ADMIN_PASSWORD = "1234";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static ArrayList<SimulatedPlayer> players = new ArrayList<>();
    private static ArrayList<GameSpecification> games = new ArrayList<>();
    private static final PartialStatePreference partialStatePreference = new PartialStatePreference(10, 10);

    public static void main(String[] args) {

        //Create a 2 new games:
        String game1Token = createGame(ADMIN_PASSWORD, 10, 10, 10, Difficulty.EASY);
        System.out.println("Game created: " + game1Token);

        String game2Token = createGame(ADMIN_PASSWORD, 50, 100, 100, Difficulty.EASY);
        System.out.println("Game created: " + game2Token);

        //List the games:
        games = listGames();

        //Select a game:
        System.out.print("Enter gameToken: ");
        String selectedGameToken = SCANNER.nextLine();

        //Find width and height of selected game:
        int width = 0;
        int height = 0;
        for (GameSpecification s : games) {
            if (s.getToken().equals(selectedGameToken)) {
                width = s.getWidth();
                height = s.getHeight();
            }
        }

        //Select num of players:
        System.out.print("Enter number of players: ");
        int numOfPlayers = SCANNER.nextInt();

        System.out.println("OK - Game: '" + selectedGameToken + "' with numOfPlayers=" + numOfPlayers);

        //Start the game:
        startGame(ADMIN_PASSWORD, selectedGameToken);
        System.out.println("Started game with token '" + selectedGameToken + "'.");

        //Let users join:
        for (int i = 0; i < numOfPlayers; i++) {
            String playerName = "Player" + (i + 1);

            String sessionID = join(selectedGameToken, playerName, partialStatePreference);
            if (sessionID == null) {
                System.out.println("Player '" + playerName + "' failed to join game '" + selectedGameToken + "'.");
            }
            else {
                players.add(new DumbSimulatedPlayer(playerName, sessionID, partialStatePreference));
                System.out.println("Player '" + playerName + "' has joined game '" + selectedGameToken + "'.");
            }
        }

        //Create the form:
        LocalAdminGameForm gameForm = new LocalAdminGameForm(selectedGameToken, width, height, partialStatePreference);
        gameForm.initialize();

        //Subscribe the admin:
        String adminSessionID = subscribe(selectedGameToken, "admin", partialStatePreference, gameForm);

        //Run the game (simulated)  -- TEST!!!:
        while (!SimulationRuntime.isGameOver(selectedGameToken)) {
            for (SimulatedPlayer s : players) {
                boolean isEnded = SimulationRuntime.makePlayerMove(selectedGameToken, s);
                if (isEnded) {
                    break;
                }
            }
        }

    }

    //Calls masterService/createGame
    private static String createGame(String password, int maxNumOfPlayers, int width, int height, Difficulty difficulty) {
        String json = adminService.createGame(password, maxNumOfPlayers, width, height, difficulty);
//        System.out.println(json);

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
//        System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status != ResponseStatus.OK) {
            JOptionPane.showMessageDialog(null, jsonObject.get(Response.MESSAGE_TAG).getAsString(), jsonObject.get(Response.TITLE_TAG).getAsString(), JOptionPane.WARNING_MESSAGE);
        }
    }

    //Calls masterService/join
    private static String join(String gameToken, String playerName, PartialStatePreference partialStatePreference) {
        String json = masterService.joinLocal(gameToken, playerName, partialStatePreference, null);
//        System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.getAsJsonObject(Response.DATA_TAG);
            return jsonObjectData.get("sessionID").getAsString();
        }

        return null;
    }

    //Calls adminService/subscribe
    private static String subscribe(String gameToken, String playerName, PartialStatePreference partialStatePreference, ObserverForm observer) {
        String json = masterService.joinLocal(gameToken, playerName, partialStatePreference, observer);
//        System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get(Response.STATUS_TAG).getAsString());

        if (status == ResponseStatus.OK) {
            JsonObject jsonObjectData = jsonObject.getAsJsonObject(Response.DATA_TAG);
            return jsonObjectData.get("sessionID").getAsString();
        }

        return null;
    }

    //Calls masterService/listGames
    public static ArrayList<GameSpecification> listGames() {
        String json = masterService.listGames();
//        System.out.println(json);

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

}
