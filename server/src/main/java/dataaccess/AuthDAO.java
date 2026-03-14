package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void addAuth(AuthData authData) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    boolean validateAuth(String authData) throws DataAccessException;

    String getUsername(String authToken) throws DataAccessException;

    void clearData() throws DataAccessException;

    int size() throws DataAccessException;
}
