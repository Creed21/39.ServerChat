import server.Server;

import java.io.IOException;

public class ServerChatApp {

    public static void main(String[] args) {
        try {
            Server chatServer = new Server();

            chatServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
