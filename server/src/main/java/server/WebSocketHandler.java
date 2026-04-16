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
        String color = null;
        ChessGame.TeamColor teamColor = null;
        ChessGame game;
        try{
            switch (commandType) {
                case CONNECT:
                    message = serializer.fromJson(ctx.message(), UserGameCommand.class);
                    if(!authDAO.validateAuth(message.getAuthToken())){
                        error(ctx, "Unauthorized Access");
                        return;
                    }
                    //what do we pass in for the 2nd thing here? We pass in the connection, but i have no clue what the syntax is for that?
                    addConnection(message.getGameID(), ctx);
                    game = gameDAO.findGame(message.getGameID()).getGame();
                    loadGame(game, ctx, message.getGameID());
                    //im thinking somehow we just pass in the username of the user who joined
                    broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "JOIN", message, "");
                    break;
                case MAKE_MOVE:
                    var makeMoveMessage = serializer.fromJson(ctx.message(), MakeMoveCommand.class);
                    if(!authDAO.validateAuth(message.getAuthToken())){
                        error(ctx, "Unauthorized Access");
                        return;
                    }
                    String username = authDAO.getUsername(message.getAuthToken());
                    GameData gameData = gameDAO.findGame(message.getGameID());
                    game = gameData.getGame();
                    if(username.equals(gameData.getBlackUsername())){
                        color = "BLACK";
                        teamColor = ChessGame.TeamColor.BLACK;
                        if(game.getTeamTurn() != TeamColor.BLACK){
                            error(ctx, "Unauthorized Access");
                            return;
                        }
                    }
                    else if(username.equals(gameData.getWhiteUsername())){
                        color = "WHITE";
                        teamColor = ChessGame.TeamColor.WHITE;
                        if(game.getTeamTurn() != TeamColor.WHITE){
                            error(ctx, "Unauthorized Access");
                            return;
                        }
                    }
                    else{
                        error(ctx, "Unauthorized Access");
                        return;
                    }
                    if(game.isInCheckmate(teamColor) || game.isInStalemate(teamColor)){
                        error(ctx, "Game over already");
                        return;
                    }
                    if(game.getGameOver()){
                        error(ctx, "Game over already");
                        return;
                    }
                    game.makeMove(makeMoveMessage.getMove());
                    gameDAO.updateGame(message.getGameID(), game);
                    if(color.equals("WHITE")){
                        if(game.isInStalemate(ChessGame.TeamColor.BLACK)){
                            broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "STALEMATE", makeMoveMessage, "Game end: Stalemate");
                        }
                        else if(game.isInCheckmate(game.getTeamTurn())){
                            String theMessage = gameData.getBlackUsername() + " is in checkmate. Game over. ";
                            broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "CHECKMATE", makeMoveMessage, theMessage);
                        }
                        else if(game.isInCheck(game.getTeamTurn())){
                            String theMessage = gameData.getBlackUsername() + " is in check";
                            broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "CHECK", makeMoveMessage, theMessage);
                        }
                    }
                    else{
                        if(game.isInStalemate(ChessGame.TeamColor.WHITE)){
                            broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "STALEMATE", makeMoveMessage, "Game end: Stalemate");
                        }
                        else if(game.isInCheckmate(game.getTeamTurn())){
                            String theMessage = gameData.getWhiteUsername() + " is in checkmate. Game over. ";
                            broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "CHECKMATE", makeMoveMessage, theMessage);
                        }
                        else if(game.isInCheck(game.getTeamTurn())){
                            String theMessage = gameData.getWhiteUsername() + " is in check";
                            broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "CHECK", makeMoveMessage, theMessage);
                        }
                    }

                    loadAllGames(game, ctx, message.getGameID());
                    broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "MOVE", makeMoveMessage, "");
                    break;

                case LEAVE:
                    if(!authDAO.validateAuth(message.getAuthToken())){
                        error(ctx, "Unauthorized Access");
                        return;
                    }
                    String username2 = authDAO.getUsername(message.getAuthToken());
                    GameData gameData2 = gameDAO.findGame(message.getGameID());
                    message = serializer.fromJson(ctx.message(), UserGameCommand.class);
                    game = gameData2.getGame();
                    message = serializer.fromJson(ctx.message(), UserGameCommand.class);
                    if(username2.equals(gameData2.getWhiteUsername())){
                        color = "WHITE";
                    }
                    if(username2.equals(gameData2.getBlackUsername())){
                        color = "BLACK";
                    }

                    if(color != null){
                        gameDAO.removePlayer(username2, message.getGameID(), color);
                    }
                    
                    broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "LEAVE", message, "");
                    removeConnection(message.getGameID(), ctx.sessionId());
                    break;

                case RESIGN:
                    message = serializer.fromJson(ctx.message(), UserGameCommand.class);
                    GameData gameData3 = gameDAO.findGame(message.getGameID());
                    game = gameData3.getGame();
                    if((authDAO.getUsername(message.getAuthToken()) != gameData3.getWhiteUsername()) 
                        || (authDAO.getUsername(message.getAuthToken()) != gameData3.getBlackUsername())){
                            
                        error(ctx, "Unauthorized Action");
                        return;
                    }
                    if(game.getGameOver()){
                        error(ctx, "Game already over");
                        return;
                    }
                    game.endGame();
                    gameDAO.updateGame(message.getGameID(), game);
                    broadcastNotification(ctx, message.getGameID(), message.getAuthToken(), "RESIGN", message, "");
                    break;
            }
            System.out.println(message);
            System.out.println("We got a message from da client! What? idk im too lazy to print it out man");
        } 
        
        catch(InvalidMoveException e){
            error(ctx, "Invalid Move");
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
        //for(var client : set){
        //    if(client.sessionId().equals(singleClient.sessionId())){
        //        continue;
        //    }
        //    client.send(json);
        //}
        singleClient.send(json);
    }

    void loadAllGames(ChessGame game, WsContext singleClient, int gameID){
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


    void broadcastNotification(WsMessageContext ctx, int gameID, String authToken, String type, UserGameCommand theMessage, String msg) throws DataAccessException{
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

        if(type.equals("STALEMATE")){
            var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, msg);
            var json = serializer.toJson(message);
            for(var client : set){
                client.send(json);
            }
        }

        if(type.equals("CHECK")){
            var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, msg);
            var json = serializer.toJson(message);
            for(var client : set){
                client.send(json);
            }
        }

        if(type.equals("CHECKMATE")){
            var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, msg);
            var json = serializer.toJson(message);
            for(var client : set){
                client.send(json);
            }
        }

        if(type.equals("LEAVE")){
            var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " left the game");
            var json = serializer.toJson(message);
            for(var client : set){
                if(client.sessionId().equals(ctx.sessionId())){
                    continue;
                }
                client.send(json);
            }
        }

        if(type.equals("RESIGN")){
            var message = new NotificationsMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " resigned. Game over!");
            var json = serializer.toJson(message);
            for(var client : set){
                client.send(json);
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
