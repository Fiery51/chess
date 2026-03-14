package service;

import java.util.Map;
import java.util.UUID;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import org.mindrot.jbcrypt.BCrypt;


public class LoginUserService {
    public Map<String, String> login(
            String username,
            String password,
            UserDAO memoryUser,
            AuthDAO memoryAuth) throws IllegalArgumentException, DataAccessException, UnauthorizedResponse {
        var user = memoryUser.findUser(username);
        if(user == null){
            //throw unauthroized exception
            throw new UnauthorizedResponse("unauthorized");
        }

        if(!BCrypt.checkpw(password, user.getPassword())){
            //throw unauthorize exception
            throw new UnauthorizedResponse("unauthorized");
        }

        //create an auth token
        String authToken = generateToken();

        //add authdata
        memoryAuth.addAuth(new AuthData(authToken, username));

        var obj = Map.of("username", username, "authToken", authToken);

        //return login result
        return obj;
    }

    private static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
