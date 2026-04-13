package server;

import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsMessageContext;

public class WebSocketHandler {
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

    public Object onConnect(WsConnectContext ctx) {
        throw new UnsupportedOperationException("Connected!");
    }

    public Object onClose(WsCloseContext ctx) {
        throw new UnsupportedOperationException("Closed!");
    }

    public Object onMessage(WsMessageContext ctx) {
        throw new UnsupportedOperationException("We got a message from da client! What? idk im too lazy to print it out man");
    }
}
