package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow extends JFrame {

    public SignUpWindow() {
        super("Sign Up Window");

        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();
    }
    public void createGUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;


        // Fields for signup
        JPasswordField passwordField = new JPasswordField(20);
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Button for signup
        JButton signUpButton = new JButton("Sign Up");
        JButton goBackButton = new JButton("Go Back");

        // Add components to the panel
        this.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        this.add(emailField, gbc);
        gbc.gridy++;
        this.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        this.add(passwordField, gbc);
        gbc.gridy++;
        this.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        this.add(usernameField, gbc);
        gbc.gridy++;
        gbc.gridy++;
        this.add(signUpButton, gbc);
        gbc.gridy++;
        this.add(goBackButton, gbc);

        // ActionListener for signup button
        signUpButton.addActionListener(e -> {
            // Validate fields
            String name = emailField.getText();
            String password = new String(passwordField.getPassword());
            String username = usernameField.getText();

            if (name.isEmpty() || password.isEmpty() || username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User(name, username, password);
                Main.getDataManager().save(user);

                new HomeWindow(user);
                this.dispose();

            }
        });

        goBackButton.addActionListener(e -> {
            new LoginWindow();
            this.dispose();
        });

        this.setVisible(true);
    }
}
