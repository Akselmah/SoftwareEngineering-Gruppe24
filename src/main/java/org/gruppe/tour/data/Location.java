package org.gruppe.tour.data;

/**
 * Represents a geographical location with postal code, address, city, and additional information.
 */
public class Location {

    private final String postalCode;
    private final String address;
    private final String city;
    private final String otherInformation;

    /**
     * Constructor to initialize a Location object.
     *
     * @param postalCode        The postal code.
     * @param address           The address.
     * @param city              The city.
     * @param otherInformation  Additional information about the location.
     */
    public Location(String postalCode, String address, String city, String otherInformation) {
        this.postalCode = postalCode;
        this.address = address;
        this.city = city;
        this.otherInformation = otherInformation;
    }

    @Override
    public String toString() {
        return postalCode + ", " + address + ", " + city + ", " + otherInformation;

    }
}