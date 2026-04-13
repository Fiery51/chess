package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsMessageContext;

public class WebSocketHandler {
    //Game id's, then we have a set of strings that are the authtokens of everyone who is connected to the current game?
    Map<Integer, Set<WsConnectContext>> connectionMap = new HashMap<>();
    public static void main(String[] args){
        Javalin.create()
            .ws("/ws", ws -> {
                ws.onConnect(ctx -> {
                    ctx.enableAutomaticPings();
                    System.out.println("Websocket connected");
                });
                ws.onMessage(ctx -> ctx.send("Websocket response: " + ctx.message()));
                ws.onClose(ctx -> System.out.println("Websocket connection closed"));
            })
        .start(8080);
    }

    public void onConnect(WsConnectContext ctx) {
        System.out.println("Connected!");
    }

    public void onClose(WsCloseContext ctx) {
        System.out.println("Closed!");
    }

    public void onMessage(WsMessageContext ctx) {
        System.out.println("We got a message from da client! What? idk im too lazy to print it out man");
        
    }

    public void addConnection(int gameID, WsConnectContext connection){
        Set<WsConnectContext> set = connectionMap.get(gameID);
        if(set == null){
            set = new HashSet<WsConnectContext>();
            connectionMap.put(gameID, set);
        }
        set.add(connection);
    }

    public void removeConnection(int gameID, WsConnectContext connection){
        Set<WsConnectContext> set = connectionMap.get(gameID);
        set.remove(connection);
    }

    void redrawChessBoardBroadcast(int gameID, String json){
        Set<WsConnectContext> set = connectionMap.get(gameID);
        for(var client : set){
            client.send(json);
        }
    }
}
