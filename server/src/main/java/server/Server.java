package server;

import service.ClearService;
import service.testMethods;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import service.testMethods;

public class Server {

    private final Javalin javalin;

    public Server() {
        MemoryAuthDAO memoryAuth = new MemoryAuthDAO();
        MemoryUserDAO memoryUser = new MemoryUserDAO();

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        //javalin.post("/user", null);
        //javalin.post("/session", null);
        //javalin.delete("session", null);
        //javalin.get("/game", null);
        //javalin.post("/game", null);
        //javalin.put("/game", null);
        javalin.delete("/db", ctx -> {
            try{
                new ClearService().clearData(memoryAuth, memoryUser);
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
