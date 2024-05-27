package server;

import model.User;
import service.LoginService;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {

    private final Socket client;
    private BufferedReader reader;
    private PrintWriter writer;
    private List<ClientThread> clients;
    private boolean running;
    private LoginService loginService;
    private User loggedUser;

    public ClientThread(Socket client, List<ClientThread> clients) throws IOException {
        this.client = client;
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        running = true;
        loginService = new LoginService();
        this.clients = clients;
    }


    @Override
    public void run() {
        while(running) {
            try {
                handleCommunication();
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    private void handleCommunication() throws IOException {
        // read request
        String request = reader.readLine();

        // do something
        // and send response
        switch (request) {
            case "login":
                String username = reader.readLine();
                String password = reader.readLine();

                User user = loginService.login(username, password); // reads username and password and tries to find credentials in db and return user object if found

                sendMessage(user.getFirstName());
                sendMessage(user.getLastName());
                sendMessage(user.getUsername());
                sendMessage(String.valueOf(user.isAdmin()));

                this.loggedUser = user;
                break;

            case "disconnect":
                closeCurrentClientConnection();
                break;

            case "sendMsg":
                sendChatMessage();
                break;

            case "createNewUser":

                break;
            default:
                sendMessage("Unsupported request");
        }
    }

    private void closeCurrentClientConnection() {
        try {
            reader.close();
            writer.close();

            running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    private void sendChatMessage() throws IOException {
        String msg = reader.readLine();
        System.out.println("--------------------------------");

        System.out.println("current users");
        for (ClientThread c: clients) {
            System.out.println(c.loggedUser.getUsername());
        }
        System.out.println("--------------------------------");

        System.out.println(loggedUser.getUsername() + ": "+msg);
        for(ClientThread c: clients) {
            if(!c.loggedUser.getUsername().equals(this.loggedUser.getUsername())) {
                sendMessage(msg);
                System.out.println("send msg to : " + c.loggedUser.getUsername());
            }
        }
    }
}
