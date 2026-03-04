package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.GameData;

public class MemoryGameDAO implements GameDAO{
    Map<Integer, GameData> dataBase = new HashMap<>();
    int tracker = 0; 

    public ArrayList<GameData> getGames(){
        ArrayList<GameData> gameList = new ArrayList<GameData>();
        for(int i = 1; i <= tracker; i++){
            gameList.add(dataBase.get(i));
        }
        return gameList;
    }

    public int createGame(String gameName){
        tracker += 1;
        dataBase.put(tracker, new GameData(tracker, null, null, gameName, null)); 
        //return the tracker so you know what the id of the game is (so you can know what the game you just made is, was originally null)
        return tracker;
    }

    public GameData findGame(Integer gameID){
        return dataBase.get(gameID);
    }

    public boolean isColorAvailable(String color, Integer gameID){
        GameData game = findGame(gameID);
        //if the game exists
        if(game != null){
            //if the color is available
            //if we're looking for white
            if(color == null){
                return false;
            }
            if(color.equals("WHITE")){
                if(game.getWhiteUsername() == null){
                    return true;
                }
            }
            else if(color.equals("BLACK")){
                if(game.getBlackUsername() == null){
                    return true;
                }
            }

        }
        return false;
    }

    public void clearData(){
        dataBase = new HashMap<>(); 
        tracker = 0;
    }

    public int size(){
        return dataBase.size();
    }

    public void insertPlayer(String username, int gameID, String color){
        GameData theGame = dataBase.get(gameID);
        GameData updated;
        if(color.equals("WHITE")){
            updated = new GameData(theGame.getGameID(), username, theGame.getBlackUsername(), theGame.getGameName(), theGame.getGame());
        }
        else{
            updated = new GameData(theGame.getGameID(), theGame.getWhiteUsername(), username, theGame.getGameName(), theGame.getGame());
        }
        dataBase.put(gameID, updated);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataBase == null) ? 0 : dataBase.hashCode());
        result = prime * result + tracker;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MemoryGameDAO other = (MemoryGameDAO) obj;
        if (dataBase == null) {
            if (other.dataBase != null)
                return false;
        } else if (!dataBase.equals(other.dataBase))
            return false;
        if (tracker != other.tracker)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MemoryGameDAO [dataBase=" + dataBase + ", tracker=" + tracker + "]";
    }

    
}
