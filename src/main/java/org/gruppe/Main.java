package org.gruppe;

import org.gruppe.data.DataManager;
import org.gruppe.data.SQLiteDataManager;
import org.gruppe.window.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    private static DataManager dataManager;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            onEnable();
            createAndShowGUI();
        });
    }

    public static void onEnable() {
        dataManager = new SQLiteDataManager(new File(System.getProperty("user.dir"), "prosjektOppgave.db"));
        dataManager.initialize();
    }

    private static void createAndShowGUI() {
        new LoginWindow();
    }

    public static DataManager getDataManager() {
        return dataManager;
    }
}

