package server;

import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        //javalin.post("/user", null);
        //javalin.post("/session", null);
        //javalin.delete("session", null);
        //javalin.get("/game", null);
        //javalin.post("/game", null);
        //javalin.put("/game", null);
        javalin.delete("/db", null);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
