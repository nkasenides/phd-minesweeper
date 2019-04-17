package services;

import api.UserService;
import model.Direction;

public class LocalMasterService implements UserService {

    @Override
    public String getPartialState(String sessionID) {
        return null;
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
