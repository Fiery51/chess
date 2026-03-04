package server;

import java.util.Map;

import com.google.gson.Gson;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import service.CreateGameService;
import service.JoinGameService;
import service.ListGamesService;
import service.UserService;

public class GameHandler {
    UserService userService;
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    MemoryGameDAO gameDAO;
    Gson gson;

    public GameHandler(UserService userService, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, MemoryGameDAO gameDAO, Gson gson){
        this.userService = userService;
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO; 
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
            int gameId = new CreateGameService().createGame(gameName, authToken, userDAO, authDAO, gameDAO);
            
            ctx.status(200);
            ctx.result(serializer.toJson(Map.of("gameID", gameId)));
        }

        catch(UnauthorizedResponse e){
            ctx.status(401);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }

        catch (DataAccessException e){
            ctx.status(500);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }
    }

    public void listGames(Context ctx){
        var serializer = new Gson();
        try{
            String authToken = ctx.header("authorization");
            if(authToken == null){
                ctx.status(401);
                ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
                return;
            }

            var obj = new ListGamesService().listGames(authToken, authDAO, gameDAO);
            ctx.status(200);
            ctx.result(serializer.toJson(Map.of("games", obj)));
        }

        catch(UnauthorizedResponse e){
            ctx.status(401);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }

        catch (DataAccessException e){
            ctx.status(500);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }
    }

    public void joinGame(Context ctx){
        var serializer = new Gson();
        try{
            String authToken = ctx.header("authorization");
            var body = serializer.fromJson(ctx.body(), Map.class);
            if(authToken == null || body == null || body.get("playerColor") == null || body.get("gameID") == null){
                ctx.status(400);
                ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
                return;
            }

            String playerColor = body.get("playerColor").toString();
            String gameID = body.get("gameID").toString();
            new JoinGameService().joinGame(playerColor, gameID, authToken, userDAO, authDAO, gameDAO);
            
            ctx.status(200);
        }

        catch (DataAccessException e){
            ctx.status(500);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }

        catch (UnauthorizedResponse e){
            ctx.status(401);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }

        catch (BadRequestResponse e){
            ctx.status(400);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }


        catch (ForbiddenResponse e){
            ctx.status(403);
            var obj = Map.of("message", "Error:" + e);
            ctx.result(serializer.toJson(obj));
        }
    }
}
