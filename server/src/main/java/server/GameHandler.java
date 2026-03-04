package server;

import java.util.Map;

import com.google.gson.Gson;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.Context;
import service.LogoutUserService;
import service.UserService;

public class GameHandler {
    UserService userService;
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    Gson gson;

    public GameHandler(UserService userService, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, Gson gson){
        this.userService = userService;
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gson = gson;
    }

    public void createGame(Context ctx){
        var serializer = new Gson();
        try{
            String authToken = ctx.header("authorization");
            var body = serializer.fromJson(ctx.body(), Map.class);
            if(authToken == null || body == null || body.get("gameName") == null){
                ctx.status(400);
                ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
                return;
            }

            String gameName = body.get("gameName").toString();
            
            
            ctx.status(200);
        }
    }
}
