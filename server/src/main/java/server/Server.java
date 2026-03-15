package server;

import service.ClearService;
import service.UserService;

import java.util.Map;

import com.google.gson.Gson;

//import dataaccess.MemoryAuthDAO;
//import dataaccess.MemoryUserDAO;
import dataaccess.*;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        try {
            new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error: ", e);
        }
        //MemoryAuthDAO memoryAuth = new MemoryAuthDAO();
        AuthDAO sqlAuth = new SQLAuthDAO(); 
        //MemoryUserDAO memoryUser = new MemoryUserDAO();
        UserDAO sqlUser = new SQLUserDAO(); 
        MemoryGameDAO memoryGame = new MemoryGameDAO();
        GameDAO sqlGame = new SQLGameDAO();

        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var serializer = new Gson();

        UserService userService = new UserService();
        UserHandler userHandler = new UserHandler(userService, sqlUser, sqlAuth, serializer);
        
        GameHandler gameHandler = new GameHandler(userService, sqlUser, sqlAuth, sqlGame, serializer);
        


        // Register your endpoints and exception handlers here.
        javalin.post("/user", ctx -> userHandler.register(ctx));

        javalin.post("/session", ctx -> userHandler.login(ctx));

        javalin.delete("/session", ctx -> userHandler.logout(ctx));

        javalin.get("/game", ctx -> gameHandler.listGames(ctx));

        javalin.post("/game", ctx -> gameHandler.createGame(ctx));

        javalin.put("/game", ctx -> gameHandler.joinGame(ctx));

        javalin.delete("/db", ctx -> {
            try{
                new ClearService().clearData(sqlAuth, sqlUser, sqlGame);
                ctx.status(200);
            }
            catch (DataAccessException e){
                ctx.status(500);
                var obj = Map.of("message", "Error:" + e);
                ctx.result(serializer.toJson(obj));
            }
            
        }
        );

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
