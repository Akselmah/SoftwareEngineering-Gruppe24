package org.gruppe.window;

import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.tour.data.Price;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TourView extends JFrame {
    private ArrayList<Tour> tours;
    private ArrayList<Tour> originalTours;
    private final User user;

    private JComboBox<String> filterOptions;
    private JButton applyFilterButton;

    // Constructor
    public TourView(ArrayList<Tour> tours, User user) {
        this.tours = tours;
        this.originalTours = tours;
        this.user = user;
        initializeUI();
    }

    // Method to set up the UI components
    private void initializeUI() {
        setTitle("Tour Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        // Add "Go Back" button to the top-left corner
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            new HomeWindow(user);
            dispose();
        });
        add(goBackButton, BorderLayout.NORTH);

        // Add filter options and apply filter button
        filterOptions = new JComboBox<>(new String[]{"None", "By Rating", "By Price"});
        applyFilterButton = new JButton("Apply Filter");
        applyFilterButton.addActionListener(e -> applyFilter());

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter By:"));
        filterPanel.add(filterOptions);
        filterPanel.add(applyFilterButton);

        add(filterPanel, BorderLayout.SOUTH);

        // Add tour details to the UI
        updateTourPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to apply the selected filter
    private void applyFilter() {
        String selectedFilter = (String) filterOptions.getSelectedItem();

        switch (selectedFilter) {
            case "By Rating":
                Collections.sort(tours, Comparator.comparing(Tour::getAverageRating));
                break;
            case "By Price":
                Collections.sort(tours, Comparator.comparing(Tour::getPriceInt));
                break;
            case "None":
                // Reset to the original order
                tours = originalTours;
                break;
            // Add more cases for additional filters as needed

            default:
                break;
        }

        // Update the UI with the filtered/sorted tours
        updateTourPanel();
    }

    // Method to update the UI with the current list of tours
    private void updateTourPanel() {
        JPanel tourPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        tourPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for (Tour tour : tours) {
            JPanel tourDetailsPanel = createTourPanel(tour);
            tourPanel.add(tourDetailsPanel);
        }

        JScrollPane scrollPane = new JScrollPane(tourPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh the UI
        revalidate();
        repaint();
    }

    // ... (existing methods)

    // Rest of your class remains unchanged

    // Method to create a panel for displaying tour details
    private JPanel createTourPanel(Tour tour) {
        JPanel panel = new JPanel(new GridLayout(0, 2)); // Two columns for each tour detail
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Add tour details to the panel
        panel.add(createLabel("Time: "));
        panel.add(createLabel(tour.getTime()));

        panel.add(createLabel("Location: "));
        panel.add(createLabel("<html>" + tour.getLocation().toString().replace("\n", "<br>") + "</html>"));

        panel.add(createLabel("Duration: "));
        panel.add(createLabel(tour.getDuration()));

        panel.add(createLabel("Price: "));
        panel.add(createLabel(tour.getPrice().toString()));
        panel.add(createLabel("Rating [0-1]: "));
        panel.add(createLabel(tour.getAverageRating() + ""));

        if(!user.isGuest()) {
            JButton joinButton = new JButton("Join Tour");
            joinButton.addActionListener(new JoinTourActionListener(tour));

            panel.add(joinButton, BorderLayout.SOUTH);
        }
        return panel;
    }

    // Helper method to create JLabel with styling
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
        return label;
    }

    // ActionListener for "Join Tour" button
    private class JoinTourActionListener implements ActionListener {
        private Tour tour;

        public JoinTourActionListener(Tour tour) {
            this.tour = tour;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle "Join Tour" button click (You can implement your logic for joining the tour here)
            // For now, let's just print a message
            new BookingWindow(user, tour);
        }
    }

}



