package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.UnauthorizedResponse;
import model.UserData;

public class LoginMemoryTest {
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;
    LoginUserService loginUserService;

    @BeforeEach
    void setup(){
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        loginUserService = new LoginUserService();

        userDAO.addUser(new UserData("test1", "test", "test"));
        userDAO.addUser(new UserData("test2", "test", "test"));
    }

    @Test
    void loggedInPositive() throws DataAccessException{
        var obj = loginUserService.login("test1", "test", userDAO, authDAO);
        Assertions.assertEquals("test1", obj.get("username"));
        Assertions.assertNotNull(obj.get("authToken"));
        Assertions.assertTrue(authDAO.validateAuth(obj.get("authToken")));
        Assertions.assertEquals("test1", authDAO.getUsername(obj.get("authToken")));

    }

    @Test
    void loggedInNegative() throws DataAccessException{
        Assertions.assertThrows(UnauthorizedResponse.class, ()
         -> loginUserService.login("test1", "thisShouldbeWRONG", userDAO, authDAO));
    }
}
