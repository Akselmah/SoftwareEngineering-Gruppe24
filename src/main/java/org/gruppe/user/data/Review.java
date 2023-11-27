package org.gruppe.user.data;

/**
 * Represents a review with a description, writer, and rating.
 */
public class Review {

    private final String reviewDescription;
    private final String writer;
    private final double rating;

    /**
     * Constructor to initialize a Review object.
     *
     * @param writer            The writer of the review.
     * @param reviewDescription The description of the review.
     * @param rating            The rating given in the review.
     */
    public Review(String writer, String reviewDescription, double rating) {
        this.writer = writer;
        this.reviewDescription = reviewDescription;
        this.rating = rating;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public double getRating() {
        return rating;
    }

    public String getWriter() {
        return writer;
    }
}
