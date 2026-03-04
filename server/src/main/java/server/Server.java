package server;

import service.ClearService;
import service.UserService;

import java.util.Map;

import com.google.gson.Gson;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        MemoryAuthDAO memoryAuth = new MemoryAuthDAO();
        MemoryUserDAO memoryUser = new MemoryUserDAO();
        MemoryGameDAO memoryGame = new MemoryGameDAO();

        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var serializer = new Gson();

        UserService userService = new UserService();
        UserHandler userHandler = new UserHandler(userService, memoryUser, memoryAuth, serializer);
        


        // Register your endpoints and exception handlers here.
        javalin.post("/user", ctx -> userHandler.register(ctx));

        javalin.post("/session", ctx -> userHandler.login(ctx));

        javalin.delete("/session", ctx -> userHandler.logout(ctx));
        //javalin.get("/game", null);

        //javalin.post("/game", null);
        
        //javalin.put("/game", null);

        javalin.delete("/db", ctx -> {
            try{
                new ClearService().clearData(memoryAuth, memoryUser, memoryGame);
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
