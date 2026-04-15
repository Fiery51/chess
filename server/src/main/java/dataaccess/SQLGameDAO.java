package dataaccess;

import java.util.ArrayList;

import model.GameData;
import com.google.gson.Gson;

import chess.ChessGame;

public class SQLGameDAO implements GameDAO {
    int tracker = 0;

    public ArrayList<GameData> getGames() throws DataAccessException{
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement("SELECT * FROM game")){
                var rs = preparedStatement.executeQuery();
                ArrayList<GameData> returnGames = new ArrayList<GameData>();
                while(rs.next()){
                    int gameID = rs.getInt("gameID");
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");
                    var serializer = new Gson();
                    String gameJson = rs.getString("game");
                    ChessGame game = serializer.fromJson(gameJson, ChessGame.class);


                    GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, game);

                    returnGames.add(gameData);
                }
                return returnGames;
            }
        } catch (Exception e){
            throw new DataAccessException("Error: ", e);
        }
    }

    public int createGame(String gameName) throws DataAccessException{
        tracker += 1;
        ChessGame game = new ChessGame(); 
        String gameJson = new Gson().toJson(game);
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement
                ("INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, null, null, ?, ?)")){
                preparedStatement.setInt(1, tracker);
                preparedStatement.setString(2, gameName);
                preparedStatement.setString(3, gameJson);
                var rs = preparedStatement.executeUpdate();
                return tracker;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public GameData findGame(Integer gameID) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("SELECT * FROM game WHERE gameID = ?")){
                preparedStatement.setInt(1, gameID);
                var rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    return null;
                }
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameName = rs.getString("gameName");
                String gameJson = rs.getString("game"); //this is json uhh deserialize this...

                var serializer = new Gson();
                ChessGame game = serializer.fromJson(gameJson, ChessGame.class);



                GameData returnGame = new GameData(gameID,
                                                   whiteUsername,
                                                   blackUsername,
                                                   gameName,
                                                   game);
                return returnGame;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public void clearData() throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("DELETE FROM game")){
                preparedStatement.executeUpdate();
                tracker = 0;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public void insertPlayer(String username, int gameID, String color) throws DataAccessException{
        String sql;
        if(color.equals("WHITE")){
            sql = "UPDATE game SET whiteUsername = ? WHERE gameID = ?";
        }
        else{
            sql = "UPDATE game SET blackUsername = ? WHERE gameID = ?";
        }

        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement(sql)){
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, gameID);
                var rs = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public int size() throws DataAccessException{
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM game")){
                var rs = preparedStatement.executeQuery();

                if(rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (Exception e){
            throw new DataAccessException("Error: ", e);
        }
    }

    public void updateGame(int gameID, ChessGame game) throws DataAccessException{
        var serializer = new Gson();
        String gameString = serializer.toJson(game);
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement("UPDATE game SET game = ? WHERE gameID = ?")){
                preparedStatement.setString(1, gameString);
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        }   catch (Exception e){
            throw new DataAccessException("Error ", e);
        }
    }

    public void removePlayer(String username, int gameID, String color) throws DataAccessException{
        String sql;
        if(color.equals("WHITE")){
            sql = "UPDATE game SET whiteUsername = NULL WHERE gameID = ?";
        }
        else{
            sql = "UPDATE game SET blackUsername = NULL WHERE gameID = ?";
        }

        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement(sql)){
                preparedStatement.setInt(1, gameID);
            preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }
}
