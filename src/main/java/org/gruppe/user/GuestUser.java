package org.gruppe.user;

/**
 * Represents a guest user with default information.
 */
public class GuestUser extends User {

    /**
     * Constructor to initialize a GuestUser object.
     */
    public GuestUser() {
        // Default information for a guest user
        super("Guest", "Guest", "password", false, true);
    }


}
