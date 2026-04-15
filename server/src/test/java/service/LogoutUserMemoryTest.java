package service;

import java.util.Map;
import java.util.zip.DataFormatException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.UnauthorizedResponse;

import model.UserData;

public class LogoutUserMemoryTest {
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;
    LoginUserService loginUserService;
    LogoutUserService logoutUserService;
    Map<String, String> obj;

    @BeforeEach
    void setup() throws DataFormatException, UnauthorizedResponse, IllegalArgumentException, DataAccessException{
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        loginUserService = new LoginUserService();
        logoutUserService = new LogoutUserService();

        userDAO.addUser(new UserData("test1", "test", "test"));
        userDAO.addUser(new UserData("test2", "test", "test"));

        obj = loginUserService.login("test1", "test", userDAO, authDAO);

    }

    @Test
    void logoutTestPositive() throws DataAccessException{
        logoutUserService.logout(obj.get("authToken"), userDAO, authDAO);
        Assertions.assertEquals(authDAO.size(), 0);
    }

    @Test
    void logoutTestNegative() throws DataAccessException{
        Assertions.assertThrows(UnauthorizedResponse.class, ()
         -> logoutUserService.logout("lets just pass something random here", userDAO, authDAO));
    }
}
