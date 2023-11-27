package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingWindow extends JFrame {
    private Tour selectedTour;
    private User user;

    // Constructor
    public BookingWindow(User user, Tour selectedTour) {
        this.selectedTour = selectedTour;
        this.user = user;
        initializeUI();
    }

    // Method to set up the UI components
    private void initializeUI() {
        setTitle("Booking for Tour: " + selectedTour.getTime() + " at " + selectedTour.getLocation());
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
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField cardNumberField = new JTextField();
        userPanel.add(new JLabel("Name: "));
        userPanel.add(nameField);
        userPanel.add(new JLabel("Email: "));
        userPanel.add(emailField);
        userPanel.add(new JLabel("Card Number: "));
        userPanel.add(cardNumberField);

        // Panel for payment button
        JPanel paymentPanel = new JPanel();
        JButton paymentButton = new JButton("Make Payment");
        paymentButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || cardNumberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(BookingWindow.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                // Simulate payment (you can add your payment logic here)
                JOptionPane.showMessageDialog(BookingWindow.this, "Payment successful!");
                Main.getDataManager().saveAttend(user, selectedTour);
                dispose(); // Close the booking window after successful payment
            }
        });
        paymentPanel.add(paymentButton);

        // Add panels to the main window
        add(detailsPanel, BorderLayout.NORTH);
        add(userPanel, BorderLayout.CENTER);
        add(paymentPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}

