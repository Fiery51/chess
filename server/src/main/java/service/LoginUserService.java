package service;

import dataaccess.MemoryUserDAO;
import model.UserData;

public class LoginUserService {
    public String[] login(String username, String password, MemoryUserDAO memoryUser){
        String[] returnData;
        var User = memoryUser.findUser(username);
        if(User == null){
            //throw unauthroized exception
        }

        if(User.getPassword() != password){
            //throw unauthorize exception
        }

        //create an auth token

        //add authdata

        //return login result
        return returnData;
    }
}
