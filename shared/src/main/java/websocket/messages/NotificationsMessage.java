package websocket.messages;

public class NotificationsMessage extends ServerMessage {

    private final String message;

    public NotificationsMessage(ServerMessageType type, String message){
        super(type);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
