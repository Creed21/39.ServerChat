package server;

import java.io.*;
import java.net.Socket;

public class ClientWritingThread extends Thread {

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader keyboard;

    public ClientWritingThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    private void sendMsgFromKeyboard() {
        try {
            System.out.print("Enter message for client: ");
            sendMsg(keyboard.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String message) {
        writer.println(message);
        writer.flush();
    }

    @Override
    public void run() {
        while (true) {
            sendMsgFromKeyboard();
        }
    }

}
