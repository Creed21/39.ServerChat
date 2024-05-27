import server.MyServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            MyServer myServer = new MyServer();
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
