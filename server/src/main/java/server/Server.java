package server;

import service.ClearService;
import service.UserService;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import io.javalin.http.ConflictResponse;
import model.AuthData;
import model.UserData;

public class Server {

    private final Javalin javalin;

    public Server() {
        MemoryAuthDAO memoryAuth = new MemoryAuthDAO();
        MemoryUserDAO memoryUser = new MemoryUserDAO();
        MemoryGameDAO memoryGame = new MemoryGameDAO();

        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var serializer = new Gson();

        // Register your endpoints and exception handlers here.
        javalin.post("/user", ctx -> {
            try{
                var body = serializer.fromJson(ctx.body(), Map.class);

                if(body == null || body.get("username") == null || body.get("password") == null || body.get("email") == null){
                    ctx.status(400);
                    ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
                    return;
                }
                
                
                String username = body.get("username").toString();
                String password = body.get("password").toString();
                String email = body.get("email").toString();
                String authToken = UserService.generateToken();

                //see if the user exists
                new UserService().getUser(username, password, email, memoryUser);

                //create the user if not
                new UserService().createUser(new UserData(username, password, email), memoryUser);
                
                //create the auth token
                new UserService().createAuth(new AuthData(authToken, username), memoryAuth);
                
                ctx.status(200);
                var obj = Map.of("username", username,"authToken", authToken);
                ctx.result(serializer.toJson(obj));

                

            }
            
            catch (JsonSyntaxException e) {
                ctx.status(400);
                ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
            }

            catch(IllegalArgumentException e){
                ctx.status(400);
                var obj = Map.of("message", "Error:" + e);
                ctx.result(serializer.toJson(obj));
            }

            catch(ConflictResponse e){
                ctx.status(403);
                var obj = Map.of("message", "Error:" + e);
                ctx.result(serializer.toJson(obj));
            }

            catch (DataAccessException e){
                ctx.status(500);
                var obj = Map.of("message", "Error:" + e);
                ctx.result(serializer.toJson(obj));
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
