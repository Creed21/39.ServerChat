package server;

import java.io.*;
import java.net.Socket;

/**
 * This class is responsible for handling communication with the client.
 * It reads messages from the client and sends messages to the client.
 * It is a thread that runs in the server.
 * <p>IT WILL BE DELETED IN THE NEXT VERSION</p>
 */
@Deprecated
public class ClientThread extends Thread {
    private final Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BufferedReader keyboard;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        handleCommunication();
    }

    private void handleCommunication() {
        while (true) {
            String msgFromClient = readMsgFromClient();
            System.out.println("Msg from client: " + msgFromClient);

            sendMsgFromKeyboard();
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

    private void sendMsgFromKeyboard() {
        try {
            System.out.print("Enter message for client: ");
            sendMsg(keyboard.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String message) {
        writer.println(message);
        writer.flush();
    }
}
