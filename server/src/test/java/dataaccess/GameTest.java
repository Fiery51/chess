package dataaccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import model.GameData;
import model.UserData;
import service.LoginUserService;

public class GameTest {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    LoginUserService loginUserService;

    @BeforeAll
    static void setupAll() throws DataAccessException{
        try {
            new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error: ", e);
        }
    }

    @BeforeEach
    void setup() throws DataAccessException{
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        gameDAO = new SQLGameDAO();

        gameDAO.clearData();
        authDAO.clearData();
        userDAO.clearData();

        loginUserService = new LoginUserService();
    }

    @Test
    void createGamePositive() throws DataAccessException{
        gameDAO.createGame("test");
        Assertions.assertEquals(1, gameDAO.size());
    }

    @Test
    void createGameNegative() throws DataAccessException{
        Assertions.assertThrows(DataAccessException.class, ()
         -> gameDAO.createGame(null));

    }


    @Test
    void findGamePositive() throws DataAccessException{
        createGamePositive();
        Assertions.assertEquals("test", gameDAO.findGame(1).getGameName());
    }


    @Test
    void findGameNegative() throws DataAccessException{
        Assertions.assertNull(gameDAO.findGame(0));
    }




    @Test
    void getGamesPositive() throws DataAccessException{
        gameDAO.createGame("test");
        gameDAO.createGame("test2");
        gameDAO.createGame("test3");
        gameDAO.createGame("test4");
        gameDAO.createGame("test5");
        ArrayList<GameData> games = gameDAO.getGames();
        Assertions.assertEquals(5, games.size());
    }

    @Test
    void getGamesNegative() throws DataAccessException{
        ArrayList<GameData> games = gameDAO.getGames();
        Assertions.assertNotNull(games);
    }

    @Test
    void insertPlayerPositive() throws DataAccessException{
        gameDAO.createGame("test");
        gameDAO.insertPlayer("test", 1, "WHITE");
        Assertions.assertEquals("test", gameDAO.findGame(1).getWhiteUsername());
    }

    @Test
    void insertPlayerNegative() throws DataAccessException{
        gameDAO.createGame("test");
        gameDAO.insertPlayer("test", 123, "white");
        GameData game = gameDAO.findGame(123);
        Assertions.assertNull(game);
    }

    @Test
    void size() throws DataAccessException{
        gameDAO.createGame("test");
        Assertions.assertEquals(1, gameDAO.size());
    }


    @Test
    void sizeTestNegative() throws DataAccessException{
        Assertions.assertEquals(0, gameDAO.size());
    }






    @Test
    void clearPositive() throws DataAccessException{
        UserData testUser = new UserData("test1", "test", "test");
        userDAO.addUser(testUser);
        Assertions.assertEquals(1, userDAO.size());
        userDAO.clearData();
        Assertions.assertEquals(0, userDAO.size());
    }
}
