package org.gruppe;

import org.gruppe.user.GuestUser;
import org.gruppe.user.User;
import org.gruppe.window.CreateTourWindow;
import org.gruppe.window.HomeWindow;
import org.gruppe.window.LoginWindow;
import org.gruppe.window.TourView;
import org.junit.Assert;
import org.junit.Test;

public class WindowTest {

    @Test
    public void testHomeWindowWithGuest() {
        Assert.assertNotNull(new HomeWindow(new GuestUser()));
    }

    @Test
    public void testHomeWindowConstructorWithNull() {
        Assert.assertThrows(Exception.class, () -> new HomeWindow(null));
    }

    @Test
    public void testBookingWindowConstructorWithNull() {
        Assert.assertNotNull(new CreateTourWindow(null));
    }

    @Test
    public void testLoginWindowConstructorWithNull() {
        Assert.assertNotNull(new LoginWindow());
    }

    @Test
    public void testTourViewConstructorWithNullTours() {
        Assert.assertThrows(NullPointerException.class, () -> new TourView(null, new User("John", "john123", "password")));
    }
}
