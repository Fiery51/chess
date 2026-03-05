package service;

import java.util.UUID;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;

import dataaccess.MemoryUserDAO;
import io.javalin.http.ConflictResponse;
import model.AuthData;
import model.UserData;

public class UserService {
    public void getUser(
            String username,
            String password,
            String email,
            MemoryUserDAO memoryUser) throws IllegalArgumentException, DataAccessException, ConflictResponse {
        UserData theUser = memoryUser.findUser(username);

        if(username == null || password == null|| email == null){
            throw new IllegalArgumentException("bad request");
        }

        if(theUser != null){
            throw new ConflictResponse("username already taken");
        }
    }

    public void createUser(UserData userData, MemoryUserDAO memoryUser) throws IllegalArgumentException{
        if(userData == null || memoryUser == null){
            throw new IllegalArgumentException("bad request");
        }
        memoryUser.addUser(userData);
    }

    public void createAuth(AuthData authData, MemoryAuthDAO memoryAuth) throws IllegalArgumentException{
        if(authData == null || memoryAuth == null){
            throw new IllegalArgumentException("bad request");
        }
        memoryAuth.addAuth(authData);
    }

    
}
