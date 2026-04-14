package dataaccess;

import java.util.ArrayList;

import chess.ChessGame;
import model.GameData;

public interface GameDAO {
    ArrayList<GameData> getGames() throws DataAccessException;

    int createGame(String gameName) throws DataAccessException; 

    GameData findGame(Integer gameID) throws DataAccessException;

    void insertPlayer(String username, int gameID, String color) throws DataAccessException;

    void clearData() throws DataAccessException;

    int size() throws DataAccessException;
    void updateGame(int gameID, ChessGame game) throws DataAccessException;
}
