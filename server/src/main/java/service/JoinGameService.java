package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.GameData;

public class JoinGameService {
    public void joinGame(String playerColor, String gameID, String authToken, MemoryUserDAO memoryUser, MemoryAuthDAO memoryAuth, MemoryGameDAO memoryGame) throws DataAccessException, UnauthorizedResponse, BadRequestResponse, ForbiddenResponse{
        
        //check if logged in
        if(!memoryAuth.validateAuth(authToken)){
            throw new UnauthorizedResponse("unauthorized");
        }

        //grab their username for later
        String username = memoryAuth.getUsername(authToken);

        //check if game exists
        GameData game = memoryGame.findGame(Integer.parseInt(gameID));
        if(game == null){
            //bad request? game doesn't exist? 
            throw new BadRequestResponse("bad request");
        }

        //check if color available
        if(playerColor.equals("WHITE")){
            if(game.getWhiteUsername() != null){
                throw new ForbiddenResponse("Already Taken");
            }
        }
        else{
            if(game.getBlackUsername() != null){
                throw new ForbiddenResponse("Already Taken");
            }
        }

        //Alright the color is not taken, we'll slot you in there
        memoryGame.insertPlayer(username, Integer.parseInt(gameID), playerColor);

        return;
    }
}
