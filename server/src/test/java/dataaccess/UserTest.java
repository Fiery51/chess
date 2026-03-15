package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.http.UnauthorizedResponse;
import model.UserData;
import service.LoginUserService;

public class UserTest {
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
    void addUserPositive() throws DataAccessException{
        UserData testUser = new UserData("test1", "test", "test");
        userDAO.addUser(testUser);
        Assertions.assertEquals(1, userDAO.size());
        Assertions.assertEquals(testUser.getUsername(), userDAO.findUser("test1").getUsername());
        Assertions.assertEquals(testUser.getEmail(), userDAO.findUser("test1").getEmail());
    }

    @Test
    void addUserNegative() throws DataAccessException{
        Assertions.assertThrows(DataAccessException.class, ()
         -> userDAO.addUser(new UserData(null, null, null)));

    }

    //________________

    @Test
    void findUserPositive() throws DataAccessException{
        addUserPositive();
    }

    @Test 
    void findUserNegative() throws DataAccessException{
        Assertions.assertNull(userDAO.findUser("hello?"));
    }

    //________

    @Test 
    void sizeTestPositive() throws DataAccessException{
        UserData testUser = new UserData("test1", "test", "test");
        userDAO.addUser(testUser);
        Assertions.assertEquals(1, userDAO.size());
    }

    @Test
    void sizeTestNegative() throws DataAccessException{
        Assertions.assertEquals(0, userDAO.size());
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
