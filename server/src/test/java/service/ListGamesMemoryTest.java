package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ListGamesMemoryTest {
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;
    CreateGameService createGameService;
    JoinGameService joinGameService;
    ListGamesService listGamesService;

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
        listGamesService = new ListGamesService(); 
        authDAO.addAuth(new AuthData("test", "test"));
    }

    @Test
    void listGamesPositive() throws DataAccessException{
        int gameID = createGameService.createGame("test", "test", userDAO, authDAO, gameDAO);
        GameData game = gameDAO.findGame(gameID);
        GameData expectedGame = new GameData(gameID, null, null, "test", null);
        Assertions.assertEquals(1, listGamesService.listGames("test", authDAO, gameDAO).size());
        Assertions.assertEquals(expectedGame, listGamesService.listGames("test", authDAO, gameDAO).get(0));
    }

    @Test
    void listGamesNegative () throws DataAccessException{
        int gameID = createGameService.createGame("test", "test", userDAO, authDAO, gameDAO);
        GameData game = gameDAO.findGame(gameID);
        Assertions.assertEquals("test", game.getGameName());
        Assertions.assertThrows(UnauthorizedResponse.class, ()
         -> listGamesService.listGames("asd fa", authDAO, gameDAO));
    }
}
