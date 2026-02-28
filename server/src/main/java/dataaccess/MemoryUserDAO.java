package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.UserData;

public class MemoryUserDAO implements UserDAO{
    Map<String, UserData> dataBase = new HashMap<>(); 
    public UserData findUser(String username){
        return dataBase.get(username);
    }

    public void addUser(UserData userData){
        dataBase.put(userData.getUsername(), userData);
    }

    public void clear(){
        dataBase = new HashMap<>(); 
    }
}
