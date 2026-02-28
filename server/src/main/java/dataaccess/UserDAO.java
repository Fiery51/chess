package dataaccess;

import model.UserData;

public interface UserDAO {
    UserData findUser(String username);
    void addUser(UserData userData);
    void clear();
}
