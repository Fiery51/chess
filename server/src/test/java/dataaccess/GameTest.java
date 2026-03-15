package dataaccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.AuthData;
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
        createGamePositive();
        createGamePositive();
        createGamePositive();
        createGamePositive();
        createGamePositive();
        ArrayList<GameData> games = gameDAO.getGames();
        Assertions.assertEquals(5, games.size());
    }

    @Test
    void getGamesNegative() throws DataAccessException{

    }

    @Test
    void insertPlayerPositive() throws DataAccessException{

    }

    @Test
    void insertPlayerNegative() throws DataAccessException{

    }

    @Test
    void size(){

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
