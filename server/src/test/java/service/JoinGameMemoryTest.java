package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.BadRequestResponse;
import model.AuthData;
import model.GameData;
import model.UserData;

public class JoinGameMemoryTest {
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;
    CreateGameService createGameService;
    JoinGameService joinGameService;

    @BeforeEach
    void setup(){
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        
        //create 2 new players for the createGamePlayers() test
        userDAO.addUser(new UserData("test1", "test", "test"));
        userDAO.addUser(new UserData("test2", "test", "test"));
        createGameService = new CreateGameService();
        joinGameService = new JoinGameService();
        authDAO.addAuth(new AuthData("test", "test"));
    }

    @Test
    void joinGamePositive() throws DataAccessException{
        int gameID = createGameService.createGame("test", "test", userDAO, authDAO, gameDAO);
        joinGameService.joinGame("WHITE", Integer.toString(gameID), "test", userDAO, authDAO, gameDAO);
        GameData updatedGame = gameDAO.findGame(gameID);
        Assertions.assertEquals("test", updatedGame.getWhiteUsername());
    }


    @Test
    void joinGameNegative() throws DataAccessException{
        int gameID = createGameService.createGame("test", "test", userDAO, authDAO, gameDAO);
        GameData game = gameDAO.findGame(gameID);
        Assertions.assertEquals("test", game.getGameName());
        Assertions.assertThrows(BadRequestResponse.class, ()
         -> new JoinGameService().joinGame("RED", Integer.toString(gameID), "test", userDAO, authDAO, gameDAO));
    }
}
