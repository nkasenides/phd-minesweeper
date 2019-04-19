package services;

import api.AdminService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import datastore.Datastore;
import model.Difficulty;
import model.Game;
import response.AuthErrorResponse;
import response.ErrorResponse;
import response.SuccessResponse;

public class LocalAdminService implements AdminService {

    @Override
    public String createGame(String password, int maxNumOfPlayers, int width, int height, Difficulty difficulty) {

        //Authentication check:
        if (!Datastore.checkPassword(password)) {
            AuthErrorResponse errorResponse = new AuthErrorResponse();
            return errorResponse.toJSON();
        }

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
            ErrorResponse errorResponse = new ErrorResponse("Game creation failed", "Failed to create game (unknown).");
            return errorResponse.toJSON();
        }

        SuccessResponse successResponse = new SuccessResponse("Game created", "A game with the specified configuration was successfully created.");
        JsonObject data = new JsonObject();
        data.addProperty("gameToken", gameToken);
        successResponse.setData(data);
        return successResponse.toJSON();
    }

    @Override
    public String startGame(String password, String gameToken) {

        //Authentication check:
        if (!Datastore.checkPassword(password)) {
            AuthErrorResponse errorResponse = new AuthErrorResponse();
            return errorResponse.toJSON();
        }

        //Find the game:
        Game referencedGame = Datastore.getGame(gameToken);
        if (referencedGame == null) {
            ErrorResponse response = new ErrorResponse("Game not found", "Game with token '" + gameToken + "' not found.");
            return response.toJSON();
        }

        //Start the game:
        if (referencedGame.start()) {
            SuccessResponse successResponse = new SuccessResponse("Game started", "Game with token '" + gameToken + "' started.");
            Gson gson = new Gson();
            JsonObject data = new JsonObject();
            data.add("gameState", gson.toJsonTree(referencedGame.getGameState()));
            successResponse.setData(data);
            return successResponse.toJSON();
        }
        else {
            ErrorResponse errorResponse = new ErrorResponse("Game already started or ended", "Game with token '" + gameToken + "' has already started or has ended.");
            Gson gson = new Gson();
            JsonObject data = new JsonObject();
            data.add("gameState", gson.toJsonTree(referencedGame.getGameState()));
            errorResponse.setData(data);
            return errorResponse.toJSON();
        }

    }

}
