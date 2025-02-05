import org.glassfish.tyrus.client.ClientManager;
import org.java_websocket.handshake.HandshakeImpl1Server;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import java.io.*;
import java.net.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.websocket.*;

@ClientEndpoint
public class ClientHandler implements Runnable{

    public static final int PORT = 6662;
    private static final JSONParser parser = new JSONParser();
    public final BufferedReader in;
    public final PrintStream out;
    private static String clientMachine = "";
    private final Socket socket;
    HttpURLConnection connection;
    URI url = new URI("wss://api.play.ai/v1/talk/sawwwaw-cJJv5dLwfSwdNXY4dS6f8");
    myWebSocketClient client;



    //const myWs = new WebSocket('wss://api.play.ai/v1/talk/sawwwaw-cJJv5dLwfSwdNXY4dS6f8');
    public ClientHandler(Socket socket) throws IOException, URISyntaxException {

        client = new myWebSocketClient(url);
        client.connect();
        //client.onOpen(new HandshakeImpl1Server());

        this.socket = socket;
        clientMachine = this.socket.getInetAddress().getHostName();
        System.out.printf("%s connected.\n",clientMachine);

        // Create the POST body data


        out = new PrintStream(this.socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                this.socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String messageFromClient;
            // Main command loop
            while ((messageFromClient = in.readLine()) != null) {
                // Converts message received from client to a JSONObject
                JSONObject clientRequest = stringToJSON(messageFromClient);
            }
        }
        catch(IOException ex){
            System.out.println("Shutting down single client server");
        } finally{
            closeQuietly();
        }
    }

    private JSONObject stringToJSON(String stringRequest) {
        try {
            return (JSONObject) parser.parse(stringRequest);
        } catch (NumberFormatException | ParseException e) {
            out.println(Response.getErrorResponse("Could not parse arguments"));
        }
        return new JSONObject();
    }

    private void closeQuietly(){
        try {
            System.out.println(String.format("%s has disconnected.\n",clientMachine));
            Response.MainServer.getThreadList().remove(this);
            socket.close();
            in.close();
            out.close();
            Response.MainServer.getClientList().remove(this);
        } catch (IOException e) {
        }
    }


    @OnOpen
    public void onOpen(Session session) {
        System.out.println ("--- Connected " + session.getId());
        try {
            session.getBasicRemote().sendText("{ \"type\": \"setup\", \"apiKey\": \"ak-7a691c95bcbc4dc19ce07c30e5f177d3\" }");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println ("--- Received " + message);
            String userInput = bufferRead.readLine();
            return userInput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {

        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);

    }


}
