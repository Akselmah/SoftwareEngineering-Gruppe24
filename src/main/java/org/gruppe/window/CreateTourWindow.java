package org.gruppe.window;

import org.gruppe.Main;
import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.tour.data.Price;
import org.gruppe.user.User;

import javax.swing.*;
import java.awt.*;

public class CreateTourWindow extends JFrame {

    private final User user;
    public CreateTourWindow(User user) {
        super("Create Tour Window");

        this.user = user;
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
        JTextField timeField = new JTextField(20);
        JTextField postalField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField cityField = new JTextField(20);
        JTextField infoField = new JTextField(20);
        JTextField durationField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        // Button for signup
        JButton createTourButton = new JButton("Create Tour");
        JButton goBackButton = new JButton("Go Back");

        // Add components to the panel
        this.add(new JLabel("Time: [10 AM]"), gbc);
        gbc.gridy++;
        this.add(timeField, gbc);
        gbc.gridy++;

        this.add(new JLabel("Address: [Storgata 13]"), gbc);
        gbc.gridy++;
        this.add(addressField, gbc);
        gbc.gridy++;

        this.add(new JLabel("PostalCode: [1767]"), gbc);
        gbc.gridy++;
        this.add(postalField, gbc);
        gbc.gridy++;
        this.add(new JLabel("City: [Halden]"), gbc);
        gbc.gridy++;
        this.add(cityField, gbc);
        gbc.gridy++;
        this.add(new JLabel("Information: [By the big red house]"), gbc);
        gbc.gridy++;
        this.add(infoField, gbc);
        gbc.gridy++;
        this.add(new JLabel("Duration: [1 hour]"), gbc);
        gbc.gridy++;
        this.add(durationField, gbc);
        gbc.gridy++;
        this.add(new JLabel("Price: [10 USD]"), gbc);
        gbc.gridy++;
        this.add(priceField, gbc);
        gbc.gridy++;
        this.add(createTourButton, gbc);
        gbc.gridy++;
        this.add(goBackButton, gbc);

        // ActionListener for signup button
        createTourButton.addActionListener(e -> {
            // Validate fields
            String address = addressField.getText();
            String time = timeField.getText();
            String postal = postalField.getText();
            String city = cityField.getText();
            String info = infoField.getText();
            String duration = durationField.getText();
            String price = priceField.getText();

            if (address.isEmpty() || time.isEmpty() || postal.isEmpty() || city.isEmpty() || info.isEmpty() || duration.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Location location = new Location(postal, address, city, info);
                Tour tour = new Tour(time, location, duration, new Price(Integer.parseInt(price.split(" ")[0]),
                        price.split(" ")[1]));

                Main.getDataManager().save(tour);

                new HomeWindow(user);
                this.dispose();

            }
        });

        goBackButton.addActionListener(e -> {
            new HomeWindow(user);
            this.dispose();
        });

        this.setVisible(true);
    }
}
