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

public class CreateGameTest {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    private CreateGameService createGameService;

    @BeforeEach
    void setup(){
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        
        //create 2 new players for the createGamePlayers() test
        userDAO.addUser(new UserData("test1", "test", "test"));
        userDAO.addUser(new UserData("test2", "test", "test"));
        createGameService = new CreateGameService();
        authDAO.addAuth(new AuthData("test", "test"));
    }

    @Test
    void createGameNoPlayers() throws DataAccessException{
        int gameID = createGameService.createGame("test", "test", userDAO, authDAO, gameDAO);
        GameData game = gameDAO.findGame(gameID);
        Assertions.assertEquals("test", game.getGameName());
        Assertions.assertNull(game.getWhiteUsername());
        Assertions.assertNull(game.getBlackUsername());
        
    }

    @Test
    void createGamePlayers() throws DataAccessException{
        int gameID = createGameService.createGame("test", "test", userDAO, authDAO, gameDAO);
        GameData game = gameDAO.findGame(gameID);
        Assertions.assertEquals("test", game.getGameName());
        Assertions.assertThrows(BadRequestResponse.class, () -> new JoinGameService().joinGame("RED", Integer.toString(gameID), "test", userDAO, authDAO, gameDAO));
    }
}