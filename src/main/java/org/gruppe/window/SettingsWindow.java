package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JLabel currentNameLabel;
    private JLabel currentDescriptionLabel;
    private final User user;
    public SettingsWindow(User user) {
        super("Settings");
        this.user = user;

        this.setSize(800, 450);

        createGUI();
    }
    public void createGUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Fields for settings
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);

        // Dummy labels for current user information
        currentNameLabel = new JLabel("Current Name: " + user.getName());
        currentDescriptionLabel = new JLabel("Current Description: " + user.getDescription());

        // Button for updating settings
        JButton updateButton = new JButton("Update Settings");
        JButton goBackButton = new JButton("Go Back");
        // Add components to the panel
        this.add(currentNameLabel, gbc); // Display current name label
        gbc.gridy++;
        this.add(currentDescriptionLabel, gbc);
        gbc.gridy++;
        gbc.gridy++;
        this.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        this.add(nameField, gbc);
        gbc.gridy++;
        this.add(new JLabel("Description:"), gbc);
        gbc.gridy++;
        this.add(descriptionField, gbc);
        gbc.gridy++;
        this.add(updateButton, gbc);
        gbc.gridy++;
        gbc.gridy++;
        this.add(goBackButton, gbc);

        updateButton.addActionListener(e -> {
            User newUser = user;
            if(!nameField.getText().isEmpty()) {
                newUser.setName(nameField.getText());
            }
            if(!descriptionField.getText().isEmpty()) {
                newUser.setDescription(descriptionField.getText());
            }
            Main.getDataManager().save(newUser);


        });

        goBackButton.addActionListener(e -> {
            new HomeWindow(user);
            this.dispose();
        });
        // Set default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window, not the entire application

        // Set initial visibility to true
        setVisible(true);

        // Pack and center the window on the screen
        setLocationRelativeTo(null);
    }

}

