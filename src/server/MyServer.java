package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private int port;
    private ServerSocket serverSocket;
    private List<ClientThread> clients;

    public MyServer() throws IOException {
        port = 3535;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<ClientThread>();
    }

    // ako je potrebno da se bira neki drugi port
    public MyServer(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<ClientThread>();
    }


    public void start() {
        System.out.println("Server is up and running . . . ");
        while(true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("new client: " + client);

                ClientThread clientThread = new ClientThread(client, clients);
                clientThread.start();

                clients.add(clientThread);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
