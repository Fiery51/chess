package dataaccess;

import model.UserData;

public interface UserDAO {
    void findUser(String username);
    void addUser(UserData userData);
    
}
