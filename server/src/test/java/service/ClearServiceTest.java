package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class ClearServiceTest {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;

    private ClearService clearService;

    @BeforeEach
    void setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        clearService = new ClearService();

        userDAO.addUser(new UserData("Test", "Test", "Test"));
        authDAO.addAuth(new AuthData("Test", "Test"));
        gameDAO.createGame("TestGame");
    }

    @Test
    void test() throws DataAccessException{
        Assertions.assertEquals(userDAO.size(), 1);
        Assertions.assertEquals(authDAO.size(), 1);
        Assertions.assertEquals(gameDAO.size(), 1);

        clearService.clearData(authDAO, userDAO, gameDAO);

        Assertions.assertEquals(userDAO.size(), 0);
        Assertions.assertEquals(authDAO.size(), 0);
        Assertions.assertEquals(gameDAO.size(), 0);
    }
}
