package gui;

import controller.Controller;
import controller.ReadingThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatForm extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Controller controller;


    public ChatForm() {
        this.controller = Controller.getInstance();
        ReadingThread readingThread = new ReadingThread(controller.getReader(), this);
        readingThread.start();

        // Set up the frame
        setTitle("Chat Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create chat area (scrollable)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Create input field and send button
        inputField = new JTextField();
        sendButton = new JButton("Send");

        // Set up panel for input and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Add key listener to the input field for "Enter" key
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append(controller.getUser().getUsername() + ": " + message + "\n");
            inputField.setText("");
            controller.sendChatMsg(message);
        }
    }

    public void appendMessage(String message) {
        chatArea.append("server: " + message + "\n");
    }
}
