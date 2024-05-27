package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    private Controller controller;

    public LoginForm() {
        controller = Controller.getInstance();

        // Set up the frame
        setTitle("Login Form");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Center the form on the screen
        setLocationRelativeTo(null);

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        // Set up layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        // Add components to the frame
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        // Add action listeners to the buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancel();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // Implement login logic here
        controller.login("login", username, password);
        JOptionPane.showMessageDialog(this, "Welcome,  " + controller.getUser());

        // open new chat form
        new ChatForm();
        this.dispose();
    }

    private void handleCancel() {
        // Clear the text fields
        this.dispose();
    }

}
