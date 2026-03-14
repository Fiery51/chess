package service;

import java.util.ArrayList;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import io.javalin.http.UnauthorizedResponse;
import model.GameData;

public class ListGamesService {
    public ArrayList<GameData> listGames(
            String authToken,
            AuthDAO memoryAuth,
            GameDAO memoryGame) throws DataAccessException, UnauthorizedResponse {
        //if you're not logged in throw an error
        if(!memoryAuth.validateAuth(authToken)){
            throw new UnauthorizedResponse("unauthorized");
        }

        //if you are the grab all the games
        return memoryGame.getGames();
    }
}
