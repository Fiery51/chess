package server;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.UserData;
import service.LoginUserService;
import service.LogoutUserTest;
import service.UserService;

public class UserHandler {
    UserService userService;
    MemoryUserDAO userDAO;
    MemoryAuthDAO authDAO;
    Gson gson;

    public UserHandler(UserService userService, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, Gson gson) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gson = gson;
    }

    public void register(Context ctx){
        var serializer = new Gson();
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
                new UserService().getUser(username, password, email, userDAO);

                //create the user if not
                new UserService().createUser(new UserData(username, password, email), userDAO);
                
                //create the auth token
                new UserService().createAuth(new AuthData(authToken, username), authDAO);
                
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
        }   

    public void login(Context ctx){
        var serializer = new Gson();
        try{
            var body = serializer.fromJson(ctx.body(), Map.class);
            if(body == null || body.get("username") == null || body.get("password") == null){
                ctx.status(400);
                ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
                return;
            }
            
            
            String username = body.get("username").toString();
            String password = body.get("password").toString();
            var obj = new LoginUserService().login(username, password, userDAO, authDAO);
            ctx.status(200);
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

    public void logout(Context ctx){
        var serializer = new Gson();
        try{
            String authToken = ctx.header("authorization");
            if(authToken == null){
                ctx.status(400);
                ctx.result(serializer.toJson(Map.of("message", "Error: bad request")));
                return;
            }
            
            new LogoutUserTest().logout(authToken, userDAO, authDAO);
            ctx.status(200);
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
}