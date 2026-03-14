package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {
    public void clearData(AuthDAO memoryAuth, UserDAO memoryUser, GameDAO memoryGame) throws DataAccessException{
        memoryAuth.clearData();
        memoryUser.clearData(); 
        memoryGame.clearData();
    }
}
