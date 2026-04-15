package server;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessGame.TeamColor;
import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import model.GameData;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
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
        ctx.session.setIdleTimeout(Duration.ofMinutes(30));
        ctx.enableAutomaticPings(15, TimeUnit.SECONDS);
    }

    public void onClose(WsCloseContext ctx) {
        Integer gameID = gameMap.get(ctx.sessionId());
        if(gameMap.get(ctx.sessionId()) == null){
            return;
        }
        removeConnection(gameID, ctx.sessionId());
        System.out.println("Closed!");
    }

    public void onMessage(WsMessageContext ctx) throws DataAccessException, InvalidMoveException {
        var serializer = new Gson();
        SQLGameDAO gameDAO = new SQLGameDAO();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        var message = serializer.fromJson(ctx.message(), UserGameCommand.class);
        UserGameCommand.CommandType commandType = message.getCommandType(); 
        String color;
        ChessGame game;
        try{
            switch (commandType) {
                case CONNECT:
                    message = serializer.fromJson(ctx.message(), UserGameCommand.class);
                    //what do we pass in for the 2nd thing here? We pass in the connection, but i have no clue what the syntax is for that?
                    addConnection(message.getGameID(), ctx);
                    game = gameDAO.findGame(message.getGameID()).getGame();
                    loadGame(game, ctx, message.getGameID());
                    //im thinking somehow we just pass in the username of the user who joined
                    broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "JOIN", message);
                    break;
                case MAKE_MOVE:
                    var makeMoveMessage = serializer.fromJson(ctx.message(), MakeMoveCommand.class);
                    if(!authDAO.validateAuth(message.getAuthToken())){
                        error(ctx, "Unauthorized Access");
                        return;
                    }
                    GameData gameData = gameDAO.findGame(message.getGameID());
                    game = gameData.getGame();
                    String username = authDAO.getUsername(message.getAuthToken());
                    if(username.equals(gameData.getBlackUsername())){
                        color = "BLACK";
                        if(game.getTeamTurn() != TeamColor.BLACK){
                            error(ctx, "Unauthorized Access");
                            return;
                        }
                    }
                    else if(username.equals(gameData.getWhiteUsername())){
                        color = "WHITE";
                        if(game.getTeamTurn() != TeamColor.WHITE){
                            error(ctx, "Unauthorized Access");
                            return;
                        }
                    }
                    else{
                        error(ctx, "Unauthorized Access");
                        return;
                    }
                    game.makeMove(makeMoveMessage.getMove());
                    gameDAO.updateGame(message.getGameID(), game);

                    loadGame(game, ctx, message.getGameID());
                    broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "MOVE", makeMoveMessage);
                    break;
            }
            System.out.println(message);
            System.out.println("We got a message from da client! What? idk im too lazy to print it out man");
        } 
        
        catch(InvalidMoveException e){
            error(ctx, "invalid move");
        }

        catch(DataAccessException e){
            error(ctx, "Server problemo");
        }

        catch(Exception e){
            e.printStackTrace();
            error(ctx, "Yeah i have no clue what happened");
        }

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

    void loadGame(ChessGame game, WsContext singleClient, int gameID){
        //we want the connection to the specific person
        //i'm just gonna have them do redraw board preetty simple, its built out and ready
        //for us. We although need to direct them to WHICH game though? 
        var serializer = new Gson();
        var message = new LoadGameMessage(ServerMessageType.LOAD_GAME, game);
        var json = serializer.toJson(message);
        Set<WsContext> set = connectionMap.get(gameID);
        for(var client : set){
            if(client.sessionId().equals(singleClient.sessionId())){
                continue;
            }
            client.send(json);
        }
        singleClient.send(json);
    }

    void broadcastNotification(WsMessageContext ctx, int gameID, String authToken, String type, UserGameCommand theMessage) throws DataAccessException{
        Set<WsContext> set = connectionMap.get(gameID);
        var serializer = new Gson();
        var recievedMessage = serializer.fromJson(ctx.message(), UserGameCommand.class);
        SQLAuthDAO authDAO = new SQLAuthDAO();
        String username = authDAO.getUsername(authToken);
        SQLGameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.findGame(recievedMessage.getGameID());
        String color;

        if(type.equals("JOIN")){
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

        if(type.equals("MOVE")){
            var newMessage = (MakeMoveCommand) theMessage; 
            ChessMove move = newMessage.getMove();
            var message2 = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " made the move: " + move.getStartPosition() + " to " + move.getEndPosition());
            var json2 = serializer.toJson(message2);
            for(var client : set){
                if(client.sessionId().equals(ctx.sessionId())){
                    continue;
                }
                client.send(json2);
            }
        }
    }

    void error(WsContext ctx, String msg){
        var serializer = new Gson();
        var errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, msg);
        var json = serializer.toJson(errorMessage);
        ctx.send(json);
    }
}
