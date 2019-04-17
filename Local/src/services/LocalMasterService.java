package services;

import api.MasterService;
import api.UserService;
import com.google.gson.JsonObject;
import com.sun.deploy.util.StringUtils;
import datastore.Datastore;
import model.*;
import response.ErrorResponse;
import response.JsonConvert;
import response.Response;
import response.SuccessResponse;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LocalMasterService implements MasterService {


    @Override
    public String createGame(int maxNumOfPlayers, int width, int height, Difficulty difficulty) {

        //Check parameters:
        if (maxNumOfPlayers < 1) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid maxNumOfPlayers", "The maximum number of players must be 1 or more.");
            return errorResponse.toJSON();
        }

        if (width < 5) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid width", "The width of a game must be 5 cells or more.");
            return errorResponse.toJSON();
        }

        if (height < 5) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid height", "The height of a game must be 5 cells or more.");
            return errorResponse.toJSON();
        }

        //Create the game:
        String gameToken = Datastore.addGame(maxNumOfPlayers, width, height, difficulty);

        if (gameToken == null) {
            ErrorResponse errorResponse = new ErrorResponse("Game creation failed", "Failed to create game.");
            return errorResponse.toJSON();
        }

        SuccessResponse successResponse = new SuccessResponse("Game created", "A game with the specified configuration was successfully created.");
        JsonObject data = new JsonObject();
        data.addProperty("gameToken", gameToken);
        successResponse.setData(data);
        return successResponse.toJSON();
    }

    @Override
    public String listGames() {
        ArrayList<String> games = Datastore.getGames();

        //Check if games exist:
        if (games.size() < 1) {
            SuccessResponse successResponse = new SuccessResponse("No games found", "No games have been found.");
            return successResponse.toJSON();
        }
        else {
            SuccessResponse successResponse = new SuccessResponse("Games retrieved", games.size() + " games retrieved.");
            JsonObject data = new JsonObject();

            class GameInstance {
                private final String token;
                private final int width;
                private final int height;
                private final String difficulty;

                public GameInstance(String token, int width, int height, Difficulty difficulty) {
                    this.token = token;
                    this.width = width;
                    this.height = height;
                    this.difficulty = difficulty.toString();
                }
            }

            ArrayList<GameInstance> gameInstances = new ArrayList<>();
            for (String token : games) {
                Game game = Datastore.getGame(token);
                gameInstances.add(new GameInstance(token, game.getGameSpecification().getWidth(), game.getFullBoardState().getHeight(), game.getGameSpecification().getDifficulty()));
            }

            data.add("games", JsonConvert.listToJsonArray(gameInstances));
            successResponse.setData(data);
            return successResponse.toJSON();
        }
    }

    @Override
    public String join(String token, String playerName, PartialStatePreference partialStatePreference) {

        //Check the game token:
        Game referencedGame = Datastore.getGame(token);

        if (referencedGame == null) {
            ErrorResponse errorResponse = new ErrorResponse("Game does not exist", "The game with token '" + token + "' does not exist.");
            return errorResponse.toJSON();
        }

        //Filter player name:
        playerName = playerName.toLowerCase().trim();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        if (!p.matcher(playerName).find()) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid player name", "The player name must contain alphanumeric characters only.");
            return errorResponse.toJSON();
        }

        //Check if the player's name exists in this game:
        for (String sessionID : Datastore.getSessions()) {
            Session s = Datastore.getSession(sessionID);
            if (s.getGameID().equals(token)) {
                if (s.getPlayerName().toLowerCase().equals(playerName)) {
                    ErrorResponse errorResponse = new ErrorResponse("Player already exists", "The player with name '" + playerName + "' already exists in game with ID '" + token + "'");
                    return errorResponse.toJSON();
                }
            }
        }

        //Create a new session:
        String sessionID = Datastore.addSession(token, playerName, partialStatePreference);
        SuccessResponse successResponse = new SuccessResponse("Game joined", "Successfully joined game with ID '" + token + "'.");
        JsonObject data = new JsonObject();
        data.addProperty("sessionID", sessionID);
        data.addProperty("totalWidth", referencedGame.getGameSpecification().getWidth());
        data.addProperty("totalHeight", referencedGame.getGameSpecification().getHeight());
        successResponse.setData(data);
        return successResponse.toJSON();
    }

}
