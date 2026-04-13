package client;

import java.net.URI;

import com.google.gson.Gson;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import websocket.commands.UserGameCommand;

public class WebsocketClient extends Endpoint{
    public Session session;
    private final String authToken;
    public int gameID;

    public WebsocketClient(String authToken, int gameID) throws Exception {
        this.authToken = authToken;
        this.gameID = gameID;
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }








    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("Connected to the server!");
        var connection = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
        var serializer = new Gson();
        String jsonVersion = serializer.toJson(connection);
        session.getAsyncRemote().sendText(jsonVersion);
    }

    public void onClose(Session session, EndpointConfig endpointConfig){
        System.out.println("Disconnected from the server!");
    }
}
