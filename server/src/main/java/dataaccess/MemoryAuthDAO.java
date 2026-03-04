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

    public void deleteAuth(String authToken){
        dataBase.remove(authToken);
    }

    public boolean validateAuth(String authToken){
        return dataBase.containsKey(authToken);
    }

    public String getUsername(String authToken){
        return dataBase.get(authToken);
    }

    public void clearData(){
        dataBase = new HashMap<>(); 
    }
}
