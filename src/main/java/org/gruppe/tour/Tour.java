package org.gruppe.tour;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AtomicDouble;
import org.gruppe.Main;
import org.gruppe.tour.data.Location;
import org.gruppe.tour.data.Price;
import org.gruppe.user.data.Review;

import java.util.Date;
import java.util.List;

/**
 * Represents a guided tour with details such as time, location, duration, and price.
 */
public class Tour {
    private final int id;
    private final String time;
    private final Location location;
    private final String duration;
    private final Price price;
    private final List<Review> reviews;

    /**
     * Constructor to initialize a Tour object.
     *
     * @param time      The time of the tour.
     * @param location  The location of the tour.
     * @param duration  The duration of the tour.
     * @param price     The price of the tour.
     */
    public Tour(int id, String time, Location location, String duration, Price price) {
        this.id = id;
        this.time = time;
        this.location = location;
        this.duration = duration;
        this.price = price;
        if(Main.getDataManager() != null) {
            this.reviews = Main.getDataManager().loadReviews(this);
        } else {
            this.reviews = Lists.newArrayList();
        }
    }

    public Tour(String time, Location location, String duration, Price price) {
        this.id = -1;
        this.time = time;
        this.location = location;
        this.duration = duration;
        this.price = price;
        if(Main.getDataManager() != null) {
            this.reviews = Main.getDataManager().loadReviews(this);
        } else {
            this.reviews = Lists.newArrayList();
        }
    }

    /**
     * Add a review to the tour.
     *
     * @param review The review to be added.
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }

    public String getDuration() {
        return duration;
    }

    public Price getPrice() {
        return price;
    }
    public int getPriceInt() {
        return price.getAmount();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Get the average rating of the tour based on reviews.
     *
     * @return The average rating.
     */
    public double getAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }

        return sum / reviews.size();
    }

    @Override
    public String toString() {
        return "Tour{" +
                "time='" + time + '\'' +
                ", location=" + location +
                ", duration='" + duration + '\'' +
                ", price=" + price +
                '}';
    }

}

