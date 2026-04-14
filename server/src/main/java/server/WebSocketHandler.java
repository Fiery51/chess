package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import model.GameData;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationsMessage;
import websocket.messages.ServerMessage;
import websocket.messages.ServerMessage.ServerMessageType;

public class WebSocketHandler {
    //Game id's, then we have a set of strings that are the authtokens of everyone who is connected to the current game?
    Map<Integer, Set<WsContext>> connectionMap = new HashMap<>();
    Map<String, Integer> gameMap = new HashMap<>();

    public void onConnect(WsConnectContext ctx) {
        System.out.println("Connected!");
    }

    public void onClose(WsCloseContext ctx) {
        Integer gameID = gameMap.get(ctx.sessionId());
        if(gameMap.get(ctx.sessionId()) == null){
            return;
        }
        removeConnection(gameID, ctx.sessionId());
        System.out.println("Closed!");
    }

    public void onMessage(WsMessageContext ctx) throws DataAccessException {
        var serializer = new Gson();
        var message = serializer.fromJson(ctx.message(), UserGameCommand.class);
        UserGameCommand.CommandType commandType = message.getCommandType(); 
        switch (commandType) {
            case CONNECT:
                //what do we pass in for the 2nd thing here? We pass in the connection, but i have no clue what the syntax is for that?
                addConnection(message.getGameID(), ctx);
                SQLGameDAO gameDAO = new SQLGameDAO();
                ChessGame game = gameDAO.findGame(message.getGameID()).getGame();
                loadGame(game, ctx);
                //im thinking somehow we just pass in the username of the user who joined
                broadcastNotification(ctx, message.getGameID(), message.getAuthToken());
                break;
            case MAKE_MOVE:

                break;
        }
        System.out.println(message);
        System.out.println("We got a message from da client! What? idk im too lazy to print it out man");
        
    }

    public void addConnection(int gameID, WsContext connection){
        Set<WsContext> set = connectionMap.get(gameID);
        if(set == null){
            set = new HashSet<WsContext>();
            connectionMap.put(gameID, set);
        }
        set.add(connection);
        gameMap.put(connection.sessionId(), gameID);
    }

    public void removeConnection(int gameID, String sessionId){
        Set<WsContext> set = connectionMap.get(gameID);
        if(set == null){
            System.out.println("How did you get here");
            gameMap.remove(sessionId);
            return;
        }
        set.removeIf(c -> c.sessionId().equals(sessionId));
        gameMap.remove(sessionId);
        if (set.isEmpty()) {
        connectionMap.remove(gameID);
    }
    }

    void redrawChessBoardBroadcast(int gameID, String json){
        Set<WsContext> set = connectionMap.get(gameID);
        if(set == null){
            System.out.println("How did we get here");
            return;
        }
        for(var client : set){
            client.send(json);
        }
    }

    void loadGame(ChessGame game, WsContext client){
        //we want the connection to the specific person
        //i'm just gonna have them do redraw board preetty simple, its built out and ready
        //for us. We although need to direct them to WHICH game though? 
        var serializer = new Gson();
        var message = new LoadGameMessage(ServerMessageType.LOAD_GAME, game);
        var json = serializer.toJson(message);
        client.send(json);
    }

    void broadcastNotification(WsMessageContext ctx, int gameID, String authToken) throws DataAccessException{
        Set<WsContext> set = connectionMap.get(gameID);
        var serializer = new Gson();
        var recievedMessage = serializer.fromJson(ctx.message(), UserGameCommand.class);
        SQLAuthDAO authDAO = new SQLAuthDAO();
        String username = authDAO.getUsername(authToken);
        SQLGameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.findGame(recievedMessage.getGameID());
        String color;
        if(gameData.getWhiteUsername().equals(username)){
            color = "white";
        }
        else if(gameData.getBlackUsername().equals(username)){
            color = "black";
        }
        else{
            color = "an observer";
        }




        var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " joined the game as " + color);
        var json = serializer.toJson(message);
        for(var client : set){
            if(client.sessionId().equals(ctx.sessionId())){
                continue;
            }
            client.send(json);
        }
    }
}
