package client;

import java.net.URI;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

public class WebsocketClient extends Endpoint{
    public Session session;

    public WebsocketClient() throws Exception {
        URI uri = new URI("http://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
                System.out.println("\nEnter another message you want to echo:");
            }
        });
    }








    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("Connected!");
    }
}
