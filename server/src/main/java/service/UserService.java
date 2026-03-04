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

    public void createUser(UserData userData, MemoryUserDAO memoryUser){
        memoryUser.addUser(userData);
    }

    public void createAuth(AuthData authData, MemoryAuthDAO memoryAuth){
        memoryAuth.addAuth(authData);
    }

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
