package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import io.javalin.http.UnauthorizedResponse;

public class LogoutUserService {
    public void logout(String authToken, UserDAO memoryUser, AuthDAO memoryAuth) throws DataAccessException, UnauthorizedResponse{
        //if you're not logged in throw an error
        if(!memoryAuth.validateAuth(authToken)){
            throw new UnauthorizedResponse("unauthorized");
        }

        //if you are logged in delete the auth
        memoryAuth.deleteAuth(authToken);

        return;
    }
}
