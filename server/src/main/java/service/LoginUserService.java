package service;

import java.util.Map;
import java.util.UUID;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;

public class LoginUserService {
    public Map<String, String> login(
            String username,
            String password,
            MemoryUserDAO memoryUser,
            MemoryAuthDAO memoryAuth) throws IllegalArgumentException, DataAccessException, UnauthorizedResponse {
        var user = memoryUser.findUser(username);
        if(user == null){
            //throw unauthroized exception
            throw new UnauthorizedResponse("unauthorized");
        }

        if(!user.getPassword().equals(password)){
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

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
