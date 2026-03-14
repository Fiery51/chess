package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import io.javalin.http.UnauthorizedResponse;

public class CreateGameService {
    public int createGame(
            String gameName,
            String authToken,
            UserDAO memoryUser,
            AuthDAO memoryAuth,
            GameDAO memoryGame) throws DataAccessException, UnauthorizedResponse {
        //if you're not logged in throw an error
        if(!memoryAuth.validateAuth(authToken)){
            throw new UnauthorizedResponse("unauthorized");
        }

        //if you are then create the game
        int gameId = memoryGame.createGame(gameName);

        return gameId; 
    }
}
