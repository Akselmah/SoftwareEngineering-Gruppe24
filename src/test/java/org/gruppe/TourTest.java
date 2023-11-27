package org.gruppe;

import org.gruppe.tour.Tour;
import org.gruppe.tour.data.Location;
import org.gruppe.tour.data.Price;
import org.gruppe.user.GuestUser;
import org.gruppe.user.data.Review;
import org.gruppe.window.HomeWindow;
import org.junit.Assert;
import org.junit.Test;

public class TourTest {

    @Test
    public void testAverageRatingWithReviews() {
        Tour tour = new Tour("5 AM", new Location("1778", "VEien 2", "Halden", " "),
                "1 hour", new Price(5, "USD"));


        tour.addReview(new Review("User1", "Great tour!", 4.5));
        tour.addReview(new Review("User2", "Awesome experience!", 5.0));

        double expectedAverageRating = (4.5 + 5.0) / 2.0;
        Assert.assertEquals(expectedAverageRating, tour.getAverageRating(), 0.01);
    }

    @Test
    public void testAverageRatingWithNoReviews() {
        Tour tour = new Tour("5 AM", new Location("1778", "VEien 2", "Halden", " "),
                "1 hour", new Price(5, "USD"));

        // No reviews added
        Assert.assertEquals(0.0, tour.getAverageRating(), 0.01);
    }

    @Test
    public void testAddReview() {
        Tour tour = new Tour("5 AM", new Location("1778", "VEien 2", "Halden", " "),
                "1 hour", new Price(5, "USD"));

        Review review = new Review("User1", "Great tour!", 4.5);
        tour.addReview(review);

        // Check if the review is added
        Assert.assertTrue(tour.getReviews().contains(review));
    }

    @Test
    public void testAddMultipleReviews() {
        Tour tour = new Tour("5 AM", new Location("1778", "VEien 2", "Halden", " "),
                "1 hour", new Price(5, "USD"));

        Review review1 = new Review("User1", "Great tour!", 4.5);
        Review review2 = new Review("User2", "Awesome experience!", 5.0);

        tour.addReview(review1);
        tour.addReview(review2);

        // Check if both reviews are added
        Assert.assertTrue(tour.getReviews().contains(review1));
        Assert.assertTrue(tour.getReviews().contains(review2));
    }

}
