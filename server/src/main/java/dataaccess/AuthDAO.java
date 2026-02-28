package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void addAuth(AuthData authData);

    void deleteAuth(AuthData authData);

    boolean validateAuth(AuthData authData);

    void clearData();
}
