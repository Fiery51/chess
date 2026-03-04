package dataaccess;

import java.util.ArrayList;

import model.GameData;

public interface GameDAO {
    ArrayList<GameData> getGames();

    int createGame(String gameName); 

    GameData findGame(Integer gameID);

    boolean isColorAvailable(String color, Integer gameID);

    void insertPlayer(String username, int gameID, String Color);

    void clearData();
}
