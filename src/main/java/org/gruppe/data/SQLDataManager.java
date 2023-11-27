package org.gruppe.data;

import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.tour.data.Price;
import org.gruppe.user.User;
import org.gruppe.user.data.Review;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

abstract class SQLDataManager implements DataManager {

    static final String TABLE_NAME_USERS = "`users`";
    static final String TABLE_NAME_USER_REVIEWS = "`reviews_user`";
    static final String TABLE_NAME_TOUR_REVIEWS = "`reviews_tour`";
    static final String TABLE_NAME_TOURS = "`tours`";
    static final String TABLE_NAME_ATTENDED_TOURS = "`attended_tours`";
    private final String url;
    private final Properties properties = new Properties();

    SQLDataManager(String url) {
        this.url = checkNotNull(url, "url");
    }

    SQLDataManager(String url, String user, String password) {
        this(url);
        this.properties.put("user", checkNotNull(user, "user"));
        this.properties.put("password", checkNotNull(password, "password"));
    }



    @Override
    public List<User> load() {

        List<User> users = new ArrayList<>();
        try (Connection connection = this.getConnection();
             PreparedStatement select = connection.prepareStatement(
                     "SELECT * FROM " + TABLE_NAME_USERS)) {

            try (ResultSet result = select.executeQuery()) {

                while (result.next()) {
                    User user = new User(result.getInt("id"), result.getString("name"),
                            result.getString("username"), result.getString("password"),
                            result.getBoolean("admin"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void save(User user) {

        try (Connection connection = this.getConnection()) {
            save(connection, user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(User user) {

        checkState(user.getId() != -1,
                "attempted to delete a user with no database ID");
        try (Connection connection = this.getConnection();
             PreparedStatement delete = connection.prepareStatement(
                     "DELETE FROM " + TABLE_NAME_USERS + " WHERE `id` = ?")) {
            delete.setInt(1, user.getId());
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new {@link Connection} to the SQL database
     * with the given properties and credentials specified
     * at creation of this data manager.
     *
     * @return The newly created connection.
     * @throws SQLException If something goes wrong while creating
     *                      the connection.
     */

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.properties);
    }
    @Override
    public void save(Tour tour) {
        try (Connection connection = this.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "INSERT INTO " + TABLE_NAME_TOURS + "(`time`, `location`, `duration`, `price`) VALUES (?, ?, ?, ?)")) {


            countStatement.setString(1, tour.getTime());
            countStatement.setString(2, tour.getLocation().toString());
            countStatement.setString(3, tour.getDuration());
            countStatement.setString(4, tour.getPrice().toString());

            countStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private synchronized void save(Connection connection, User user) throws SQLException {

        if (user.getId() == -1) {

            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME_USERS + "(`name`, `username`, `password`, `admin`) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                insert.setString(1, user.getName());
                insert.setString(2, user.getUsername());
                insert.setString(3, user.getPassword());
                insert.setBoolean(4, user.isAdmin());

                insert.executeUpdate();
                try (ResultSet result = insert.getGeneratedKeys()) {

                    if (result.next()) {
                        user.setId(result.getInt(1));
                    }
                }
            }

            return;
        }

        try (PreparedStatement update = connection.prepareStatement(
                "UPDATE " + TABLE_NAME_USERS + " SET `name`, `username`, `password`, `admin`, `description` = ?, ?, ?, ?, ? WHERE `id` = ?")) {
            update.setString(1, user.getName());
            update.setString(2, user.getUsername());
            update.setString(3, user.getPassword());
            update.setBoolean(4, user.isAdmin());
            update.setString(5, user.getDescription());
            update.setInt(6, user.getId());
            update.executeUpdate();
        }
    }


    @Override
    public User authenticate(String username, String password) {
        // This current code is susceptible to SQL injection attacks and uses plain text passwords,
        // In the future change it to hashing passwords for better security
        User user = null;
        try (Connection connection = this.getConnection();
             PreparedStatement select = connection.prepareStatement(
                     "SELECT * FROM " + TABLE_NAME_USERS + " WHERE `name` = ? AND `password` = ?" )) {

            select.setString(1, username);
            select.setString(2, password);
            try (ResultSet result = select.executeQuery()) {

                if(result.next()) {
                    user = new User(result.getInt("id"), result.getString("name"),
                            result.getString("username"), result.getString("password"),
                            result.getBoolean("admin"));
                    user.setDescription(result.getString("description"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public List<Review> loadReviews(Tour tour) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = this.getConnection();
             PreparedStatement select = connection.prepareStatement(
                     "SELECT * FROM " + TABLE_NAME_TOUR_REVIEWS + " WHERE `tourid` = ?")) {

            select.setInt(1, tour.getId());
            try (ResultSet result = select.executeQuery()) {

                while (result.next()) {
                    Review review = new Review(result.getString("writer"),
                            result.getString("description"),
                            result.getDouble("rating"));
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reviews;
    }


    @Override
    public List<Tour> loadTours(User user) {
        List<Tour> tours = new ArrayList<>();
        try (Connection connection = this.getConnection();
             PreparedStatement select = connection.prepareStatement(
                     "SELECT t.* " +
                             "FROM " + TABLE_NAME_ATTENDED_TOURS + " att " +
                             "JOIN " + TABLE_NAME_TOURS + " t ON att.tourid = t.id " +
                             "WHERE att.userid = ?")) {

            select.setInt(1, user.getId());
            try (ResultSet result = select.executeQuery()) {
                while (result.next()) {
                    // Extract tour information
                    int tourId = result.getInt("id");
                    String time = result.getString("time");
                    String locationString = result.getString("location");
                    String duration = result.getString("duration");
                    String priceString = result.getString("price");

                    Price price = new Price(Integer.parseInt(priceString.split(" ")[0]), priceString.split(" ")[1]);

                    Location location = new Location(locationString.split(", ")[0],
                            locationString.split(", ")[1], locationString.split(", ")[2], locationString.split(", ")[3]);

                    Tour tour = new Tour(tourId, time, location, duration, price);

                    // Add the tour to the list
                    tours.add(tour);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tours;
    }

    @Override
    public void saveAttend(User user, Tour tour) {
            try (Connection connection = this.getConnection();
                 PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME_ATTENDED_TOURS + "(`tourid`, `userid`) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                insert.setInt(1, tour.getId());
                insert.setInt(2, user.getId());

                insert.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    @Override
    public ArrayList<Tour> loadTours() {
        ArrayList<Tour> tours = new ArrayList<>();
        try (Connection connection = this.getConnection();
             PreparedStatement select = connection.prepareStatement("SELECT * FROM " + TABLE_NAME_TOURS)) {

            try (ResultSet result = select.executeQuery()) {
                while (result.next()) {

                    int tourId = result.getInt("id");
                    String time = result.getString("time");
                    String locationString = result.getString("location");
                    String duration = result.getString("duration");
                    String priceString = result.getString("price");

                    Price price = new Price(Integer.parseInt(priceString.split(" ")[0]), priceString.split(" ")[1]);

                    Location location = new Location(locationString.split(", ")[0],
                            locationString.split(", ")[1], locationString.split(", ")[2], locationString.split(", ")[3]);

                    Tour tour = new Tour(tourId, time, location, duration, price);

                    tours.add(tour);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tours;
    }

    @Override
    public void saveReview(User user, Tour tour, Review review) {
        try (Connection connection = this.getConnection();
             PreparedStatement insert = connection.prepareStatement(
                     "INSERT INTO " + TABLE_NAME_TOUR_REVIEWS + "(`tourid`, `description`, `writer`, `rating`) VALUES (?, ?, ?, ?)"
             )) {

            insert.setInt(1, tour.getId());
            insert.setString(2, review.getReviewDescription());
            insert.setString(3, user.getUsername());
            insert.setDouble(4, review.getRating());

            insert.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
