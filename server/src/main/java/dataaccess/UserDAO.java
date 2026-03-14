package dataaccess;

import model.UserData;

public interface UserDAO {
    UserData findUser(String username) throws DataAccessException;
    void addUser(UserData userData) throws DataAccessException;
    void clearData() throws DataAccessException;
    int size() throws DataAccessException;
}
