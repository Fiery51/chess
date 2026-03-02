package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class ClearService {
    public void clearData(MemoryAuthDAO memoryAuth, MemoryUserDAO memoryUser, MemoryGameDAO memoryGame) throws DataAccessException{
        memoryAuth.clearData();
        memoryUser.clearData(); 
        memoryGame.clearData();
    }
}
