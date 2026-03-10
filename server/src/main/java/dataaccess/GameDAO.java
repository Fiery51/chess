package dataaccess;

import java.util.ArrayList;

import model.GameData;

public interface GameDAO {
    ArrayList<GameData> getGames();

    int createGame(String gameName); 

    GameData findGame(Integer gameID);

    void insertPlayer(String username, int gameID, String color);

    void clearData();

    int size();
}
