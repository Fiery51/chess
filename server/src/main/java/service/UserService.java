package service;


import dataaccess.AuthDAO;
import dataaccess.DataAccessException;

import dataaccess.UserDAO;
import io.javalin.http.ConflictResponse;
import model.AuthData;
import model.UserData;

public class UserService {
    public void getUser(
            String username,
            String password,
            String email,
            UserDAO memoryUser) throws IllegalArgumentException, DataAccessException, ConflictResponse {
        UserData theUser = memoryUser.findUser(username);

        if(username == null || password == null|| email == null){
            throw new IllegalArgumentException("bad request");
        }

        if(theUser != null){
            throw new ConflictResponse("username already taken");
        }
    }

    public void createUser(UserData userData, UserDAO memoryUser) throws IllegalArgumentException, DataAccessException{
        if(userData == null || memoryUser == null){
            throw new IllegalArgumentException("bad request");
        }
        memoryUser.addUser(userData);
    }

    public void createAuth(AuthData authData, AuthDAO memoryAuth) throws IllegalArgumentException, DataAccessException{
        if(authData == null || memoryAuth == null){
            throw new IllegalArgumentException("bad request");
        }
        memoryAuth.addAuth(authData);
    }

    
}
