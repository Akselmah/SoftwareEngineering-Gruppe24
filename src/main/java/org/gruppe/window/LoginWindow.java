package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.user.GuestUser;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    public LoginWindow() {
        super("Login Window");

        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();
    }

    public void createGUI() {
        // Create JLabels, JTextFields, and JPasswordField for username and password
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Create a JButton for login
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");
        JButton loginAsGuest = new JButton("Login as Guest");

        JLabel resultLabel = new JLabel("");
        // Add an ActionListener to handle button clicks
        loginButton.addActionListener(e -> {
            // Retrieve the entered username and password
            String username = emailField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            // Perform authentication or any other action based on username and password
            User user = Main.getDataManager().authenticate(username, password);
            if (user != null) {
                new HomeWindow(user);
                this.dispose();

            } else {
                resultLabel.setText("Login failed. Invalid username or password.");
                resultLabel.setForeground(Color.RED); // Set text color to red for failure
            }
        });

        signUpButton.addActionListener(e -> {
            new SignUpWindow();
            this.dispose();
        });

        loginAsGuest.addActionListener(e -> {
            new HomeWindow(new GuestUser());
            this.dispose();
        });

        // Set layout manager to GridBagLayout
        this.setLayout(new GridBagLayout());

        // Create GridBagConstraints for centering components
        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Position and add components to the content pane
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        emailField.setColumns(10); // Set column width
        this.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span two columns for the button
        this.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(loginAsGuest, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.gridwidth = 3; // Span three columns for the result label
        this.add(resultLabel, gbc);

        // Set the window to be visible
        this.setVisible(true);
    }

}
