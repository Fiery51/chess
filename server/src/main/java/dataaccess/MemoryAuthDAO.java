package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.AuthData;

public class MemoryAuthDAO implements AuthDAO{
    //authtoken, username
    Map<String, String> dataBase = new HashMap<>(); 
    
    public void addAuth(AuthData authData){
        dataBase.put(authData.getAuthToken(), authData.getUsername());
    }

    public void deleteAuth(AuthData authData){
        dataBase.remove(authData.getAuthToken());
    }

    public boolean validateAuth(AuthData authData){
        return dataBase.containsKey(authData.getAuthToken());
    }

    public void clearData(){
        dataBase = new HashMap<>(); 
    }
}
