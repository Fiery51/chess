package dataaccess;

import java.util.ArrayList;

import model.GameData;

public interface GameDAO {
    ArrayList<GameData> getGames();

    void createGame(); 

    GameData findGame(Integer gameID);

    boolean isColorAvailable(String color, Integer gameID);

    void clearData();
}
