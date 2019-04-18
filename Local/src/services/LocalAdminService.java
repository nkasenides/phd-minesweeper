package services;

import api.AdminService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import datastore.Datastore;
import model.Game;
import response.ErrorResponse;
import response.SuccessResponse;

public class LocalAdminService implements AdminService {

    @Override
    public String startGame(String adminPassword, String gameToken) {
        if (Datastore.checkPassword(adminPassword)) {
            Game referencedGame = Datastore.getGame(gameToken);
            if (referencedGame == null) {
                ErrorResponse response = new ErrorResponse("Game not found", "Game with token '" + gameToken + "' not found.");
                return response.toJSON();
            }
            if (referencedGame.start()) {
                SuccessResponse successResponse = new SuccessResponse("Game started", "Game with token '" + gameToken + "' started.");
                Gson gson = new Gson();
                JsonObject data = new JsonObject();
                data.add("gameState", gson.toJsonTree(referencedGame.getGameState()));
                return successResponse.toJSON();
            }
            else {
                ErrorResponse errorResponse = new ErrorResponse("Game already started or ended", "Game with token '" + gameToken + "' has already started or has ended.");
                Gson gson = new Gson();
                JsonObject data = new JsonObject();
                data.add("gameState", gson.toJsonTree(referencedGame.getGameState()));
                return errorResponse.toJSON();
            }
        }
        else {
            ErrorResponse errorResponse = new ErrorResponse("Authentication error", "The password you provided is not valid.");
            return errorResponse.toJSON();
        }
    }

}
