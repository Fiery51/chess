package model;

import chess.ChessGame;

public class GameData {
    private final int gameID;
    private final String whiteUsername;
    private final String blackUsername;
    private final String gameName;
    private final ChessGame game;
    
    public GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    

    public int getGameID() {
        return gameID;
    }



    public String getWhiteUsername() {
        return whiteUsername;
    }



    public String getBlackUsername() {
        return blackUsername;
    }



    public String getGameName() {
        return gameName;
    }



    public ChessGame getGame() {
        return game;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + gameID;
        result = prime * result + ((whiteUsername == null) ? 0 : whiteUsername.hashCode());
        result = prime * result + ((blackUsername == null) ? 0 : blackUsername.hashCode());
        result = prime * result + ((gameName == null) ? 0 : gameName.hashCode());
        result = prime * result + ((game == null) ? 0 : game.hashCode());
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
        GameData other = (GameData) obj;
        if (gameID != other.gameID)
            return false;
        if (whiteUsername == null) {
            if (other.whiteUsername != null)
                return false;
        } else if (!whiteUsername.equals(other.whiteUsername))
            return false;
        if (blackUsername == null) {
            if (other.blackUsername != null)
                return false;
        } else if (!blackUsername.equals(other.blackUsername))
            return false;
        if (gameName == null) {
            if (other.gameName != null)
                return false;
        } else if (!gameName.equals(other.gameName))
            return false;
        if (game == null) {
            if (other.game != null)
                return false;
        } else if (!game.equals(other.game))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GameData [gameID=" + gameID + ", whiteUsername=" + whiteUsername + ", blackUsername=" + blackUsername
                + ", gameName=" + gameName + ", game=" + game + "]";
    }

    
}
