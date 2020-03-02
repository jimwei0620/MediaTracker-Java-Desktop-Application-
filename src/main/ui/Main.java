package ui;

import javax.swing.*;
import java.io.IOException;

// Main
public class Main {
    //Effects: begins the tracker app
    public static void main(String[] args) throws IOException {
        //new TrackerApp();
        TrackerAppGUI trackerApp = new TrackerAppGUI();
        trackerApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackerApp.setSize(400, 400);
        trackerApp.setVisible(true);
    }
}
