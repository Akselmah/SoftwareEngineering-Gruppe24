package org.gruppe.window;

import com.google.common.collect.Lists;
import org.gruppe.Main;
import org.gruppe.tour.Tour;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AttendedToursWindow extends JFrame {
    private List<Tour> tours;
    private final User user;

    // Constructor
    public AttendedToursWindow(User user) {
        this.user = user;
        this.tours = Main.getDataManager().loadTours(user);
        initializeUI();
    }

    // Method to set up the UI components
    private void initializeUI() {
        setTitle("Attended Tours");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the border on the content pane
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());

        // Add "Go Back" button to the top-left corner
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            new HomeWindow(user);
            dispose();

        });
        add(goBackButton, BorderLayout.NORTH);

        if(!tours.isEmpty()) {
            // Add tour details to the UI
            JPanel tourPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Three columns with 10px spacing
            for (Tour tour : tours) {
                JPanel tourDetailsPanel = createTourPanel(tour);
                tourPanel.add(tourDetailsPanel);

            }

            // Use JScrollPane to allow scrolling if there are many tours
            JScrollPane scrollPane = new JScrollPane(tourPanel);
            add(scrollPane, BorderLayout.CENTER);

        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

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

        JButton joinButton = new JButton("Leave review");
        joinButton.addActionListener(new JoinTourActionListener(tour, user));
        panel.add(joinButton, BorderLayout.SOUTH);
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
        private User user;

        public JoinTourActionListener(Tour tour, User user) {
            this.tour = tour;
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle "Join Tour" button click (You can implement your logic for joining the tour here)
            // For now, let's just print a message
            new ReviewWindow(user, tour);
        }
    }

}
