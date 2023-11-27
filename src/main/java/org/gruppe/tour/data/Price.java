package org.gruppe.tour.data;

/**
 * Represents the price of a tour with an amount and currency.
 */
public class Price {

    private final int amount;
    private final String currency;

    /**
     * Constructor to initialize a Price object.
     *
     * @param amount    The amount of the price.
     * @param currency  The currency of the price.
     */
    public Price(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

}

