package server;

import service.ClearService;
import service.UserService;

import java.util.Map;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import io.javalin.http.ConflictResponse;

public class Server {

    private final Javalin javalin;

    public Server() {
        MemoryAuthDAO memoryAuth = new MemoryAuthDAO();
        MemoryUserDAO memoryUser = new MemoryUserDAO();
        MemoryGameDAO memoryGame = new MemoryGameDAO();

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.post("/user", ctx -> {
            try{
                String username = ctx.bodyAsClass(Map.class).get("username").toString();
                new UserService().getUser(username, memoryAuth, memoryUser, memoryGame);
                ctx.status(200);
            }

            catch(IllegalArgumentException e){
                ctx.status(400);
                ctx.result("Error:" + e);
            }

            catch(ConflictResponse e){
                ctx.status(403);
                ctx.result("Error:" + e);
            }

            catch (DataAccessException e){
                ctx.status(500);
                ctx.result("Error:" + e);
            }
        });
        //javalin.post("/session", null);
        //javalin.delete("session", null);
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
                ctx.result("Error:" + e);
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
