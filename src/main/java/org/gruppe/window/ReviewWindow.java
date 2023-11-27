package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.tour.Tour;
import org.gruppe.user.User;
import org.gruppe.user.data.Review;

import javax.swing.*;
import java.awt.*;

public class ReviewWindow extends JFrame {
    private Tour selectedTour;
    private User user;

    // Constructor
    public ReviewWindow(User user, Tour selectedTour) {
        this.selectedTour = selectedTour;
        this.user = user;
        initializeUI();
    }

    // Method to set up the UI components
    private void initializeUI() {
        setTitle("Review for Tour: " + selectedTour.getTime() + " at " + selectedTour.getLocation());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose the window when closed

        setLayout(new BorderLayout());

        // Panel for tour details
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2));
        detailsPanel.add(new JLabel("Time: "));
        detailsPanel.add(new JLabel(selectedTour.getTime()));
        detailsPanel.add(new JLabel("Location: "));
        detailsPanel.add(new JLabel(selectedTour.getLocation().toString()));
        detailsPanel.add(new JLabel("Duration: "));
        detailsPanel.add(new JLabel(selectedTour.getDuration()));
        detailsPanel.add(new JLabel("Price: "));
        detailsPanel.add(new JLabel(selectedTour.getPrice().toString()));

        // Panel for user information
        JPanel userPanel = new JPanel(new GridLayout(0, 2));
        JTextField descriptionField = new JTextField();
        JTextField ratingField = new JTextField();
        userPanel.add(new JLabel("Description of review: "));
        userPanel.add(descriptionField);
        userPanel.add(new JLabel("Rating of Tour 0-1: "));
        userPanel.add(ratingField);

        // Panel for payment button
        JPanel paymentPanel = new JPanel();
        JButton reviewButton = new JButton("Make Payment");
        reviewButton.addActionListener(e -> {
            if (ratingField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(ReviewWindow.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                // Simulate payment (you can add your payment logic here)
                JOptionPane.showMessageDialog(ReviewWindow.this, "Review Sent successful!");
                Review review = new Review(user.getUsername(), descriptionField.getText(), Double.parseDouble(ratingField.getText()));
                Main.getDataManager().saveReview(user, selectedTour, review);
                dispose(); // Close the booking window after successful payment
            }
        });
        paymentPanel.add(reviewButton);

        // Add panels to the main window
        add(detailsPanel, BorderLayout.NORTH);
        add(userPanel, BorderLayout.CENTER);
        add(paymentPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
