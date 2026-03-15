package service;

import java.util.zip.DataFormatException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.UserData;

public class UserServiceMemoryTest {
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;

    UserService userService;

    @BeforeEach
    void setup(){
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        userService = new UserService(); 
        userDAO.addUser(new UserData("test1", "test", "test"));
    }




    @Test
    void getUserPositive() throws DataAccessException{
        Assertions.assertDoesNotThrow(() -> userService.getUser("test2", "test", "test", userDAO));
    }

    @Test
    void getUserNegative() throws DataAccessException{
        Assertions.assertThrows(IllegalArgumentException.class, ()
         -> userService.getUser("null", null, null, userDAO));
    }

    @Test
    void createUserPositive() throws DataAccessException{
        Assertions.assertDoesNotThrow(() -> userService.createUser(new UserData("TEST", "TEST", "TEST"), userDAO));
    }

    @Test
    void createUserNegative() throws DataAccessException{
        Assertions.assertThrows(IllegalArgumentException.class, ()
         -> userService.createUser(null, userDAO));
    }

    @Test
    void createAuthPositive() throws DataAccessException{
        Assertions.assertDoesNotThrow(() -> userService.createAuth(new AuthData("TEST", "TEST"), authDAO));
    }

    @Test
    void createAuthNegative() throws DataAccessException{
        Assertions.assertThrows(IllegalArgumentException.class, ()
         -> userService.createAuth(null, authDAO));
    }
}
