package org.gruppe.user;

import org.gruppe.user.data.Review;

import java.util.List;

/**
 * The make user class that represents a client
 * and manages the data for that client.
 */
public class User {

    private int id;

    private String name;
    private final String username;
    private final String password;
    private final boolean admin;
    private final boolean guest;
    private String description = " ";

    // Have a constructor for GuestUser Class
    public User(String name, String username, String password, boolean admin, boolean guest) {
        this.id = -1;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.guest = guest;
    }
    // Have one constructor for creating a admin user
    public User(int id, String name, String username, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.guest = false;
    }

    public User(String name, String username, String password) {
        this.id = -1;
        this.name = name;
        this.username = username;
        this.password = password;
        // Set admin variable to false as a default
        this.admin = false;
        this.guest = false;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public final String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGuest() {
        return guest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    public void setName(String name) {
        this.name = name;
    }

}
