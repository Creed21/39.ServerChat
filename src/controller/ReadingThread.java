package controller;

import gui.ChatForm;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadingThread extends Thread {
    private BufferedReader reader;
    private ChatForm chatApp;

    public ReadingThread(BufferedReader reader, ChatForm chatApp) {
        this.reader = reader;
        this.chatApp = chatApp;
    }

    private String readMsgFromServer() {
        try {
            String msg = reader.readLine();
            chatApp.appendMessage(msg);
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while(true) {
            String msgFromServer = readMsgFromServer();
            System.out.println("Server: " + msgFromServer);
            if(msgFromServer == null) {
                System.out.println("connection broken");
                break;
            }
        }
    }
}
