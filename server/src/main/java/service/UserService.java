package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.ConflictResponse;
import model.UserData;

public class UserService {
    public UserData getUser(String username, MemoryAuthDAO memoryAuth, MemoryUserDAO memoryUser, MemoryGameDAO memoryGame) throws IllegalArgumentException, DataAccessException, ConflictResponse{
        UserData theUser = memoryUser.findUser(username);

        if(username == null){
            throw new IllegalArgumentException("bad request");
        }

        //if(theUser != null){
        //    throw new ConflictResponse("username already taken");
        //}
        return theUser; 
        
        
    }
}
