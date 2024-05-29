package server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientReadingThread extends Thread {

    private Socket clientSocket;
    private BufferedReader reader;
    private List<ClientWritingThread> writingThreadList;

    public ClientReadingThread(Socket clientSocket, List<ClientWritingThread> writingThreadList) {
        this.clientSocket = clientSocket;
        this.writingThreadList = writingThreadList;
        try {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    private String readMsgFromClient() {
        try {
            String msg = reader.readLine();
            for (ClientWritingThread writingThread : writingThreadList) {
                if(!writingThread.getClientSocket().equals(clientSocket))
                    writingThread.sendMsg(msg);
            }
            return msg;
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
            if(msgFromClient == null) {
                System.out.println("connection broken");
                break;
            }
        }
    }

}
