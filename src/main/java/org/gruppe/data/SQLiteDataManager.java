package org.gruppe.data;

import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.user.User;

import java.io.File;
import java.sql.*;

public class SQLiteDataManager extends SQLDataManager {

    public SQLiteDataManager(File databaseFile) {
        super("jdbc:sqlite:" + databaseFile.getAbsolutePath());
    }

    @Override
    public void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = this.getConnection();
             PreparedStatement create = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER_REVIEWS + "(" +
                             "`id` INTEGER PRIMARY KEY," +
                             "`userid` INTEGER NOT NULL," +
                             "`description` VARCHAR(100) NOT NULL," +
                             "`writer` VARCHAR(100) NOT NULL," +
                             "`rating` DOUBLE NOT NULL)")) {
            create.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = this.getConnection();
             PreparedStatement create = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TOUR_REVIEWS + "(" +
                             "`id` INTEGER PRIMARY KEY," +
                             "`tourid` INTEGER NOT NULL," +
                             "`description` VARCHAR(100) NOT NULL," +
                             "`writer` VARCHAR(100) NOT NULL," +
                             "`rating` DOUBLE NOT NULL)")) {
            create.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = this.getConnection();
             PreparedStatement create = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TOURS + "(" +
                             "`id` INTEGER PRIMARY KEY," +
                             "`time` VARCHAR(100) NOT NULL," +
                             "`location` VARCHAR(100) NOT NULL," +
                             "`duration` VARCHAR(100) NOT NULL," +
                             "`price` VARCHAR(100) NOT NULL)")) {
            create.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = this.getConnection();
             PreparedStatement create = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ATTENDED_TOURS + "(" +
                             "`id` INTEGER PRIMARY KEY," +
                             "`tourid` INTEGER NOT NULL," +
                             "`userid` INTEGER NOT NULL)")) {
            create.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = this.getConnection();
             PreparedStatement create = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USERS + "(" +
                             "`id` INTEGER PRIMARY KEY," +
                             "`name` VARCHAR(100) NOT NULL," +
                             "`username` VARCHAR(100) NOT NULL," +
                             "`password` VARCHAR(100) NOT NULL," +
                             "`admin` BOOLEAN NOT NULL," +
                             "`description` VARCHAR(100) NOT NULL DEFAULT `No Description yet`)")) {
            create.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a admin user for handling other users
        // This is a bad way of including a admin user, works for testing but not commercial use.
        // TODO Create a better way of including an admin user.
        try (Connection connection = this.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "INSERT INTO " + TABLE_NAME_USERS + "(`id`, `name`, `username`, `password`, `admin`) VALUES (?, ?, ?, ?, ?)")) {

            countStatement.setInt(1, 1);
            countStatement.setString(2, "admin");
            countStatement.setString(3, "admin");
            countStatement.setString(4, "admin123");
            countStatement.setBoolean(5, true);

            countStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a dummy tour for testing
        try (Connection connection = this.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "INSERT INTO " + TABLE_NAME_TOURS + "(`time`, `location`, `duration`, `price`) VALUES (?, ?, ?, ?)")) {


            countStatement.setString(1, "10 AM");
            countStatement.setString(2, new Location("1767", "VeienVeien 3", "Halden", " ").toString());
            countStatement.setString(3, "1 hour");
            countStatement.setString(4, "45 USD");

            countStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
