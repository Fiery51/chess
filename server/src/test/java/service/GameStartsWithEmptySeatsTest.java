package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;

public class GameStartsWithEmptySeatsTest {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    private CreateGameService createGameService;

    @BeforeEach
    void setup(){
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
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
}
