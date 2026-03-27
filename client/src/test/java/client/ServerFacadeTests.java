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
        
    }

    @Test
    public void createGameNegative(){

    }

    @Test
    public void listGamesPositive(){

    }

    @Test
    public void listGamesNegative(){

    }

    @Test
    public void playGamePositive(){

    }

    @Test
    public void playGameNegative(){

    }

    @Test
    public void observeGamePositive(){

    }

    @Test
    public void observeGameNegative(){

    }

}
