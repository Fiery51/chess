package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.UnauthorizedResponse;

public class CreateGameService {
    public int createGame(String gameName, String authToken, MemoryUserDAO memoryUser, MemoryAuthDAO memoryAuth, MemoryGameDAO memoryGame) throws DataAccessException, UnauthorizedResponse{
        //if you're not logged in throw an error
        if(!memoryAuth.validateAuth(authToken)){
            throw new UnauthorizedResponse("unauthorized");
        }

        //if you are then create the game
        int gameId = memoryGame.createGame(gameName);

        return gameId; 
    }
}
