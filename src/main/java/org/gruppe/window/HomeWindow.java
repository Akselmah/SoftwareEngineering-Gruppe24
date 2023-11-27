package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.tour.data.Price;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeWindow extends JFrame {

    private final User user;
    public HomeWindow(User user) {
        super("Home");
        this.user = user;

        this.setSize(800, 450);

        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createGUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton settingsButton = new JButton("Settings");
        JButton createTour = new JButton("Create Tour");
        JButton viewTours = new JButton("View All Tours");
        JButton attendedTours = new JButton("Attended Tours");

        createTour.addActionListener(e -> {
            new CreateTourWindow(user);
            this.dispose();
        });
        attendedTours.addActionListener(e -> {
            new AttendedToursWindow(user);
            this.dispose();
        });

        settingsButton.addActionListener(e -> {
            new SettingsWindow(user);
            this.dispose();
        });

        viewTours.addActionListener(e -> {

            SwingUtilities.invokeLater(() -> new TourView(Main.getDataManager().loadTours(), user));
            this.dispose();
        });

        if(!user.isGuest()) {
            this.add(settingsButton, gbc);
            gbc.gridy++;
        }
        if(user.isAdmin()) {
            this.add(createTour, gbc);
            gbc.gridy++;
        }
        this.add(viewTours, gbc);
        gbc.gridy++;
        if(!user.isGuest()) {
            this.add(attendedTours, gbc);
            gbc.gridy++;
        }

        // Set initial visibility to true
        setVisible(true);

        // center the window on the screen
        setLocationRelativeTo(null);

    }


}