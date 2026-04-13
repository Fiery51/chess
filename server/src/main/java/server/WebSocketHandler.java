package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationsMessage;
import websocket.messages.ServerMessage;
import websocket.messages.ServerMessage.ServerMessageType;

public class WebSocketHandler {
    //Game id's, then we have a set of strings that are the authtokens of everyone who is connected to the current game?
    Map<Integer, Set<WsContext>> connectionMap = new HashMap<>();

    public void onConnect(WsConnectContext ctx) {
        System.out.println("Connected!");
    }

    public void onClose(WsCloseContext ctx) {
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
                broadcastNotification(message.getGameID(), message.getAuthToken());
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
    }

    public void removeConnection(int gameID, WsContext connection){
        Set<WsContext> set = connectionMap.get(gameID);
        set.remove(connection);
    }

    void redrawChessBoardBroadcast(int gameID, String json){
        Set<WsContext> set = connectionMap.get(gameID);
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

    void broadcastNotification(int gameID, String authToken) throws DataAccessException{
        Set<WsContext> set = connectionMap.get(gameID);
        var serializer = new Gson();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        String username = authDAO.getUsername(authToken);
        var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " joined the game");
        var json = serializer.toJson(message);
        for(var client : set){
            client.send(json);
        }
    }
}
