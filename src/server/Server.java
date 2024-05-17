package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 3535;
    private ServerSocket serverSocket;

    // init server
    public Server() throws IOException {
        try {
            this.serverSocket = new ServerSocket(port); // ako nije uspela inicijalizacija -> baci exception sa custom porukom
            System.out.println("Server is running . . .");
        } catch (IOException e) {
            throw new IOException("Failed to initialize server!");
        }
    }

    // start
    public void startServer() {
        while(true) {
            System.out.println("Waiting for new client connection. . .");
            try {
                Socket clientSocket = serverSocket.accept();
                Thread newClientThread = new ClientThread(clientSocket);
                System.out.println("new client connection started: " + clientSocket);
                newClientThread.start();
            } catch (IOException e) {
                System.out.println("Failed to accept client connection");
                System.out.println(e.getMessage());
                e.printStackTrace();

            }
        }
    }

}
