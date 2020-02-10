package ui;


import exceptions.EmptyStringException;
import model.ListManager;
import model.MediaList;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


import persistence.Reader;
import persistence.Writer;

//Media Tracker application Modeled after TellerApp example given for now
public class TrackerApp {
    private static final String LIST_FILE = "./data/lists.json";
    private ListManager listColl;
    private Scanner input; //Input for console.

    // EFFECTS: Runs Tracker application
    public TrackerApp() throws IOException {
        runTracker();
    }

    /*
    * MODIFIES: this
    * EFFECTS: process user input
    * */
    private void runTracker() throws IOException {
        boolean appIsRunning = true;
        String menuCommand;

        initApp();
        loadInfo();

        while (appIsRunning) {
            displayListColl();
            displayMainMenuOptions();
            menuCommand = input.nextLine();
            menuCommand = menuCommand.toLowerCase();

            if (menuCommand.equals("q")) {
                saveLists();
                appIsRunning = false;
            } else {
                processMenuCommand(menuCommand);
            }
        }

        System.out.println("Exiting application...Goodbye!\n");
    }

    // MODIFIES: this
    // EFFECTS: load info from LIST_FILE. Catch NullPointerException when there are no info in LIST_FILE
    private void loadInfo() throws IOException {
        ArrayList<MediaList> listRead = Reader.readFile(LIST_FILE);
        try {
            for (MediaList list: listRead) {
                listColl.addToColl(list);
            }
        } catch (NullPointerException e) {
            System.out.println("There are no lists stored!");
        }
    }

    // EFFECTS: save state of listColl to LISTS_FILE. Modeled after TellerApp
    private void saveLists() {
        try {
            Writer writer = new Writer(new File(LIST_FILE));
            writer.write(listColl);
            writer.close();
            System.out.println("All lists are saved to file " + LIST_FILE);
        } catch (IOException e) {
            System.out.println("Unable to save accounts to " + LIST_FILE);
            e.printStackTrace();
        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: initialize stored lists
    * */
    private void initApp() {
        listColl = new ListManager();
        input = new Scanner(System.in);
    }

    /*
     * EFFECTS: processes Menu commands from the user
     * */
    private void processMenuCommand(String command) {
        switch (command) {
            case "1": //switch right now for further development purposes
                processArgument("add");
                return;
            case "2" :
                processArgument("delete");
                return;
            case "3":
                processArgument("select");
                return;
            default:
                System.out.println("Sorry...that command is invalid. Please try again!\n");
        }
    }

    /*
    * EFFECTS: displays and prompts arguments required to complete commands. Restarts if processOperation fails.
    * */
    private void processArgument(String textCommand) {
        String argument;
        Boolean processingArgument = true;

        while (processingArgument) {
            System.out.println("Type the name of the list to " + textCommand + ".");
            System.out.println("Type \"CANCEL\" to cancel this operation.\n");
            argument = input.nextLine();
            if (argument.equals("CANCEL")) {
                System.out.println("Operation was cancelled.\n");
                processingArgument = false;
            } else {
                processingArgument = !processOperation(textCommand, argument);
            }
        }
    }


    /*
     * EFFECTS: process operation specified with argument. returns true if operation is successful else return false
     *  */
    private Boolean processOperation(String op, String argument) {
        switch (op) {
            case "add":
                return addNewList(argument);
            case "delete":
                return deleteList(argument);
            case "select":
                return startListApp(argument);
            default:
                System.out.println("Internal Error!");
                return false;
        }
    }


    /*
    * MODIFIES: This
    * EFFECTS: add a new list to listColl, returns true if successful. Catches EmptyStringException
    * */
    private Boolean addNewList(String argument) {
        try {
            MediaList newMediaList = new MediaList(argument);
            listColl.addToColl(newMediaList);
            System.out.println("\"" + argument + "\" was successfully added.\n");
            return true;
        } catch (EmptyStringException e) {
            System.out.println("Name of the new list cannot be empty! Try again!");
            return false;
        }
    }

    /*
    * MODIFIES: This
    * EFFECTS: Delete first list found with name argument, return true if deleted, return false if list with
    *          such name was not found. Catches EmptyStringException
    * */
    private Boolean deleteList(String argument) {
        try {
            MediaList mediaListToDelete = listColl.findMediaListByName(argument);
            if (mediaListToDelete == null) {
                System.out.println("Sorry, list with name \"" + argument + "\" was not found. Please try again.");
                return false;
            } else {
                listColl.remove(mediaListToDelete);
                System.out.println("\"" + argument + "\" was successfully deleted.\n");
                return true;
            }
        } catch (EmptyStringException e) {
            System.out.println("Name of the list to delete cannot be empty! Try again!");
            return false;
        }
    }

    /*
    * EFFECTS: start ListApp if list with listName is in listColl and return true, else return false;
    * Catches EmptyStringException
    * */
    private Boolean startListApp(String listName) {
        try {
            MediaList listToView = listColl.findMediaListByName(listName);
            if (listToView == null) {
                System.out.println("Sorry! the list with name \"" + listName + "\" was not found! Please try again.");
                return false;
            } else {
                new ListUI(listToView);
                return true;
            }
        } catch (EmptyStringException e) {
            System.out.println("Name of the list cannot be empty! Try again!");
            return false;
        }
    }

    /*
    * EFFECTS: display listCOll
    * */
    private void displayListColl() {
        String listCollString = "Your lists: ";
        System.out.println("There are currently \"" + listColl.size() + "\" lists.");
        Iterator<MediaList> list = listColl.getList().iterator(); //Adapted from Java iterator documentation
        while (list.hasNext()) {
            listCollString += "\"" + list.next().getName() + "\"";
            if (list.hasNext()) {
                listCollString += ", ";
            }
        }
        System.out.println(listCollString);
    }

    /*
     * EFFECTS: display Main menu options to user
     * */
    private void displayMainMenuOptions() {
        System.out.println("Choose an option:");
        System.out.println("\t1 -> Create a new list");
        System.out.println("\t2 -> Delete a list");
        System.out.println("\t3 -> View a list");
        System.out.println("\tq -> Quit application");
    }


}
