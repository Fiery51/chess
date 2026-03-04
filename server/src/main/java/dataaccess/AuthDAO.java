package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void addAuth(AuthData authData);

    void deleteAuth(String authToken);

    boolean validateAuth(String authData);

    String getUsername(String authToken);

    void clearData();

    int size();
}
