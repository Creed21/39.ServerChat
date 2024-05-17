package server;

import java.io.*;
import java.net.Socket;

public class ClientReadingThread extends Thread {

    private BufferedReader reader;

    public ClientReadingThread(Socket clientSocket) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readMsgFromClient() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            String msgFromClient = readMsgFromClient();
            System.out.println("Msg from client: " + msgFromClient);
        }
    }

}
