package ui;

import model.MediaList;

import java.util.Scanner;

//Media Tracker application Modeled after TellerApp example given for now
public class TrackerApp {
    private MediaList listOfShows;
    private Scanner input; //Input for console.

    // EFFECTS: Runs Tracker application
    public TrackerApp() {
        runTracker();
    }

    /*
    * MODIFIES: this
    * EFFECTS: process user input
    * */
    private void runTracker() {
        boolean appIsRunning = true;
        String menuCommand;

        initApp();

        while (appIsRunning) {
            displayMainMenuOptions();
            menuCommand = input.next();
            menuCommand = menuCommand.toLowerCase();

            if (menuCommand.equals("q")) {
                appIsRunning = false;
            } else {
                processMenuCommand(menuCommand);
            }
        }

        System.out.println("Exiting application...Goodbye!\n");
    }

    /*
    * MODIFIES: this
    * EFFECTS: initialize stored lists
    * */
    private void initApp() {
        listOfShows = new MediaList("List of Shows");
        input = new Scanner(System.in);
    }

    /*
     * EFFECTS: processes Menu commands from the user
     * */
    private void processMenuCommand(String command) {
        switch (command) {
            case "1": //switch right now for further development purposes
                new ListApp(listOfShows);
                return;
            default:
                System.out.println("Sorry...that command is invalid. Please try again!\n");
        }
    }

    /*
     * EFFECTS: display Main menu options to user
     * */
    private void displayMainMenuOptions() {
        System.out.println("Choose a list:\n");
        System.out.println("\t1 -> Open list\n");
        System.out.println("\tq -> Quit application\n");
    }
}
