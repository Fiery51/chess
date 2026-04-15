package dataaccess;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import model.AuthData;
import model.UserData;
import service.LoginUserService;

public class AuthTest {
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
    void addAuthPositive() throws DataAccessException{
        UserData testUser = new UserData("test1", "test", "test");
        userDAO.addUser(testUser);
        Assertions.assertEquals(1, userDAO.size());
        Assertions.assertEquals(testUser.getUsername(), userDAO.findUser("test1").getUsername());
        Assertions.assertEquals(testUser.getEmail(), userDAO.findUser("test1").getEmail());


        AuthData testAuth = new AuthData("test", "test1");
        authDAO.addAuth(testAuth);
        Assertions.assertTrue(authDAO.validateAuth("test"));

    }

    @Test
    void addauthNegative() throws DataAccessException{
        Assertions.assertThrows(DataAccessException.class, ()
         -> authDAO.addAuth(new AuthData(null, null)));

    }


    @Test
    void deleteAuthPositive() throws DataAccessException{
        addAuthPositive();
        authDAO.deleteAuth("test");
        Assertions.assertEquals(0, authDAO.size());

    }

    @Test
    void deleteAuthNegative() throws DataAccessException{
        addAuthPositive();
        Assertions.assertDoesNotThrow(() -> authDAO.deleteAuth("missingToken"));
    }


    @Test
    void validateAuthPositive() throws DataAccessException{
        addAuthPositive();
        Assertions.assertTrue(authDAO.validateAuth("test"));
    }


    @Test 
    void validateAuthNegative() throws DataAccessException{
        addAuthPositive();
        Assertions.assertFalse(authDAO.validateAuth("tesa;lsdkjf;laskjdft"));
    }



    @Test
    void getUsernamePositive() throws DataAccessException{
        addAuthPositive();
        Assertions.assertEquals("test1", authDAO.getUsername("test"));
    }

    @Test
    void getUsernameNegative() throws DataAccessException{
        addAuthPositive();
        Assertions.assertNull(authDAO.getUsername("masdf asdf"));
    }

    @Test
    void size() throws DataAccessException{
        UserData testUser = new UserData("test1", "test", "test");
        userDAO.addUser(testUser);
        AuthData testAuth = new AuthData("test", "test1");
        authDAO.addAuth(testAuth);
        Assertions.assertEquals(1, authDAO.size());
    }

@Test
    void sizeTestNegative() throws DataAccessException{
        Assertions.assertEquals(0, authDAO.size());
    }







    @Test
    void clearPositive() throws DataAccessException{
        size();
        authDAO.clearData();
        Assertions.assertEquals(0, authDAO.size());
    }
}
