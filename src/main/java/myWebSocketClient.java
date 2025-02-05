
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class myWebSocketClient extends WebSocketClient{

    String jsonInputString = "{ \"type\": \"setup\", \"apiKey\": \"ak-7a691c95bcbc4dc19ce07c30e5f177d3\" }";

    public myWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send(jsonInputString);
        System.out.println("Opened connection");
    }

    @Override
    public void onMessage(String message) {
        //send(message);
        System.out.println("Sent: " + message);  // Print received message from server
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed connection. Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

}
