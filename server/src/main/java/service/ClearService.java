package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

public class ClearService {
    public void clearData(MemoryAuthDAO memoryAuth, MemoryUserDAO memoryUser) throws DataAccessException{
        memoryAuth.clearData();
        memoryUser.clearData(); 
    }
}
