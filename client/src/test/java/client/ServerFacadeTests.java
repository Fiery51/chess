package client;

import java.io.IOException;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    ServerFacade facade = new ServerFacade();


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    void setup(){
        facade.deleteRequest();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerPositive() throws IOException, InterruptedException{
        var result = facade.registerRequest("test", "test", "test");
        Assertions.assertNotEquals("Error", result[0]);
        Assertions.assertEquals("200", result[1]);
    }

    @Test
    public void registerNegative() throws IOException, InterruptedException{
        var result = facade.registerRequest("test", "test", "test");
        var result2 = facade.registerRequest("test", "test", "test");
        Assertions.assertEquals("Error", result2[0]);
        Assertions.assertEquals("403", result2[1]);
    }

    @Test
    public void loginPositive() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var result = facade.loginRequest("test", "test");
        Assertions.assertEquals("200", register[1]);
        Assertions.assertNotEquals("Error", result[0]);
        Assertions.assertEquals("200", result[1]);
    }

    @Test
    public void loginNegative() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var result = facade.loginRequest("test", "wrongPassword");
        Assertions.assertEquals("401", result[1]);
    }

    @Test
    public void logoutPositive() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var result = facade.logoutRequest(login[0]);

        Assertions.assertEquals(200, result);
    }

    @Test
    public void logoutNegative() throws IOException, InterruptedException{
        var result = facade.logoutRequest("what if i pass in an invalid auth token :shrug:");

        Assertions.assertEquals(401, result);
    }

    @Test
    public void createGamePositive() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var result = facade.createGame(login[0], "testGame");

        Assertions.assertEquals(200, result[0]);
        Assertions.assertEquals(1, result[1]);
    }

    @Test
    public void createGameNegative() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var result = facade.createGame("yeah i ain't giving you something good", "testGame");

        Assertions.assertEquals(401, result[0]);
    }

    @Test
    public void listGamesPositive() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var createGame = facade.createGame(login[0], "testGame");
        var result = facade.listGames(login[0]);

        Assertions.assertEquals("testGame", result.games().get(1));
    }

    @Test
    public void listGamesNegative() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var createGame = facade.createGame(login[0], "testGame");
        var result = facade.listGames("oops");

        Assertions.assertEquals(401, result.statusCode());
    }

    @Test
    public void playGamePositive() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var createGame = facade.createGame(login[0], "testGame");
        var result = facade.playGame(login[0], "WHITE", "1");

        Assertions.assertEquals(200, result);
    }

    @Test
    public void playGameNegative() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var register2 = facade.registerRequest("test2", "test2", "test2");

        var login = facade.loginRequest("test", "test");
        var createGame = facade.createGame(login[0], "testGame");
        var joinGame1 = facade.playGame(login[0], "WHITE", "1");
        var logout = facade.logoutRequest(login[1]);

        var login2 = facade.loginRequest("test2", "test2");
        var result = facade.playGame(login2[0], "WHITE", "1");
        

        Assertions.assertEquals(403, result);
    }

    @Test
    public void observeGamePositive() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var createGame = facade.createGame(login[0], "testGame");
        var result = facade.observeGame(login[0], "1");

        Assertions.assertEquals(200, result);
    }

    @Test
    public void observeGameNegative() throws IOException, InterruptedException{
        var register = facade.registerRequest("test", "test", "test");
        var login = facade.loginRequest("test", "test");
        var createGame = facade.createGame(login[0], "testGame");
        var result = facade.observeGame(login[0], "5");

        Assertions.assertEquals(400, result);
    }

}
