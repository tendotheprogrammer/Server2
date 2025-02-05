import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.*;

public class Response {

    public static String getErrorResponse(String message){
        JSONObject response = new JSONObject();
        HashMap<String,String> dataMap = new HashMap<>(Map.of("message",message));
        response.put("result", "ERROR");
        response.put("data", dataMap);
        return response.toJSONString();
    }


    public static class MainServer {

        private static final List<Thread> threadList = new ArrayList<Thread>();
        private static final List<ClientHandler> clientList = new ArrayList<ClientHandler>();

        private static void StartMultiserver() throws IOException {
        // Creates new socket
        ServerSocket s = new ServerSocket(ClientHandler.PORT);

            while(true) {
                try {
                    Socket socket = s.accept();
                    System.out.println("Connection: " + socket);
                    ClientHandler r = new ClientHandler(socket);
                    clientList.add(r);
                    Thread task = new Thread(r);
                    threadList.add(task);
                    task.start();
                } catch(IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public static void main(String[] args) throws IOException {
            String localHostIPAddress = InetAddress.getLocalHost().getHostAddress();
            String strIPAddress = String.format("%-10s: %20s","Local IP",localHostIPAddress);
            String strPort = String.format("%-10s: %20d","Port",ClientHandler.PORT);
            System.out.println(strIPAddress);
            System.out.println(strPort);
            System.out.println("Server running & waiting for client connections.");
            MainServer server = new MainServer();
            new Thread(new ServerConsole()).start();
            MainServer.StartMultiserver();
        }

        public static List<Thread> getThreadList() {
            return threadList;
        }

        public static List<ClientHandler> getClientList(){
            return clientList;
        }

    }
}
