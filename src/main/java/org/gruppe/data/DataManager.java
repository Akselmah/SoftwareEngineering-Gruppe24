package org.gruppe.data;

import org.gruppe.tour.Tour;
import org.gruppe.user.User;
import org.gruppe.user.data.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle basic loading and saving of users
 * data to an implementation based database.
 */
public interface DataManager {

    /**
     * Initialize the database if it needs to be initialized.
     */
    void initialize();

    /**
     * Load and return a mutable list of all the users
     * from the database.
     *
     * @return The users.
     */
    List<User> load();

    /**
     * Save and add a single user to the database.
     *
     * @param user The user to save.
     */
    void save(User user);

    /**
     * Save and add a single tour to the database.
     *
     * @param tour The tour to save.
     */
    void save(Tour tour);
    /**
     * Authenticate the given credentials.
     *
     * @param username The users' username.
     * @param password The users' password.
     * @return The user.
     */
    User authenticate(String username, String password);


    /**
     * Delete the given user from the database.
     *
     * @param user The user to delete.
     */
    void delete(User user);

    /**
     * Load and return a mutable list of all the reviews
     * for a given tour.
     *
     * @param tour The tour's reviews.
     * @return The reviews.
     */
    List<Review> loadReviews(Tour tour);

    /**
     * Load and return a mutable list of all the tours
     * from the user given.
     *
     * @param user The user's tours.
     * @return The tours.
     */
    List<Tour> loadTours(User user);

    /**
     * Load and return a mutable list of all the tours.
     *
     * @return The tours.
     */
    ArrayList<Tour> loadTours();

    /**
     * Save a user attending a tour
     *
     * @param user The user to save.
     */
     void saveAttend(User user, Tour tour);

    /**
     * Save a user review for a given tour
     *
     * @param user The user.
     */
    void saveReview(User user, Tour tour, Review review);
}
