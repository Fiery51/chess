package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.UnauthorizedResponse;

public class LogoutUserService {
    public void logout(String authToken, MemoryUserDAO memoryUser, MemoryAuthDAO memoryAuth) throws DataAccessException, UnauthorizedResponse{
        //if you're not logged in throw an error
        if(!memoryAuth.validateAuth(authToken)){
            throw new UnauthorizedResponse("unauthorized");
        }

        //if you are logged in delete the auth
        memoryAuth.deleteAuth(authToken);

        return;
    }
}
