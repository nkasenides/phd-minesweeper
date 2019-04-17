package services;

import api.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import datastore.Datastore;
import model.*;
import model.exception.InvalidCellReferenceException;
import response.ErrorResponse;
import response.Response;
import response.ResponseStatus;
import response.SuccessResponse;

public class LocalUserService implements UserService {

    @Override
    public String getPartialState(String sessionID) {

        //Retrieve referenced session and check if valid:
        Session referencedSession = Datastore.getSession(sessionID);

        //If session not valid, return error:
        if (referencedSession == null) {
            Response response = new ErrorResponse("Session not found", "Could not find session with ID '" + sessionID + "'");
            return response.toJSON();
        }

        //If session valid, try to get the referenced game:
        else {

            Game referencedGame = Datastore.getGame(referencedSession.getGameID());

            //If game not found for this session, return error:
            if (referencedGame == null) {
                Response response = new ErrorResponse("Game not found", "Could not find a game for the session with ID '" + sessionID + "'");
                return response.toJSON();
            }

            //If game found, return its partial state:
            else {
                PartialStatePreference partialStatePreference = referencedSession.getPartialStatePreference();
                try {
                    PartialBoardState partialBoardState = new PartialBoardState(partialStatePreference.getWidth(), partialStatePreference.getHeight(), referencedSession.getPositionX(), referencedSession.getPositionY(), referencedGame.getFullBoardState());
                    Response response = new SuccessResponse("Partial state retrieved", "Partial state retrieved.");
                    Gson gson = new Gson();
                    JsonObject data = new JsonObject();
                    data.add("partialBoardState", gson.toJsonTree(partialBoardState));
                    response.setData(data);
                    return response.toJSON();
                }

                //If failed to get the partial state, return error:
                catch (InvalidCellReferenceException e) {
                    e.printStackTrace();
                    Response response = new ErrorResponse("Error fetching partial state for session '" + sessionID + "'.", e.getMessage());
                    return response.toJSON();
                }
            }
        }
    }

    @Override
    public String move(String sessionID, Direction direction, int unitOfMovement) {
        return null;
    }

    @Override
    public String reveal(String sessionID, int x, int y) {
        return null;
    }

    @Override
    public String flag(String sessionID, int x, int y) {
        return null;
    }

}
