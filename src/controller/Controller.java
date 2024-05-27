package controller;

import model.User;

import java.io.*;
import java.net.Socket;

public class Controller {

    private static Controller instance;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean running;
    private User user;

    private Controller() throws IOException {
        socket = new Socket("localhost", 3535);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        running = true;
        System.out.println("connected: " + socket);
    }

    public static Controller getInstance() {
        if (instance == null) {
            try {
                instance = new Controller();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public User getUser() {
        return user;
    }

    public void login(String action, String username, String password)  {
        // send request
        try {
            sendMessage("login");
            sendMessage(username);
            sendMessage(password);

            user = new User();

            user.setFirstName(reader.readLine());
            user.setLastName(reader.readLine());
            user.setUsername(reader.readLine());
            user.setAdmin(Boolean.parseBoolean(reader.readLine()));

            System.out.println("User: " + user);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public String readMessage() throws IOException {
        return reader.readLine();
    }

    public void sendChatMsg(String message) {
        sendMessage("sendMsg");
        sendMessage(message);
    }

}
