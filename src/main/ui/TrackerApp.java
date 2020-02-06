package ui;

import model.ListManager;
import model.MediaItem;
import model.MediaList;

import java.util.Iterator;
import java.util.Scanner;

//Media Tracker application Modeled after TellerApp example given for now
public class TrackerApp {
    private ListManager listColl;
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
        displayListColl();

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
        listColl = new ListManager();
        input = new Scanner(System.in);
    }

    /*
     * EFFECTS: processes Menu commands from the user
     * */
    private void processMenuCommand(String command) {
        switch (command) {
            case "1": //switch right now for further development purposes
                addOrDeleteList("add");
                return;
            case "2" :
                addOrDeleteList("del");
                return;
            case "3":
                selectList();
                return;
            default:
                System.out.println("Sorry...that command is invalid. Please try again!\n");
        }
    }

    /*
    * REQUIRES: Operation name to be "del" or "add"
    * MODIFIES: This
    * EFFECTS: add or deletes a list
    * */
    private void addOrDeleteList(String op) {
        String listToEdit;
        Boolean editingList = true;

        System.out.println("Type the name of the media to " + op + ".\n");
        System.out.println("Type CANCEL to cancel this operation.\n");

        while (editingList) {
            listToEdit = input.next();
            if (listToEdit.equals("")) {
                System.out.println("Sorry! the name of the list cannot be empty! Please try again.\n");
                System.out.println("Type CANCEL to cancel the operation.\n");
            } else if (listToEdit.equals("CANCEL")) {
                System.out.println("Operation was cancelled.\n");
                editingList = false;
            } else {
                editingList = !processOperation(op, listToEdit);
            }
        }
    }

    /*
     * REQUIRES: Operation name to be "del" or "add"
     * EFFECTS: process operation specified. returns true if operation is successful else false
     *  */
    private Boolean processOperation(String op, String listToEdit) {
        if (op == "add") {
            MediaList newMediaList = new MediaList(listToEdit);
            listColl.addToColl(newMediaList);
            System.out.println(listToEdit + " was successfully added.\n");
            return true;
        } else {
            MediaList mediaListToDelete = listColl.findMediaListByName(listToEdit);
            if (mediaListToDelete == null) {
                System.out.println("Sorry, list with name " + listToEdit + " was not found. Please try again.");
                return false;
            } else {
                listColl.remove(mediaListToDelete);
                System.out.println(listToEdit + " was successfully deleted.\n");
                return true;
            }
        }
    }

    /*
    * EFFECTS: process user input to select and view a list
    * */
    private void selectList() {
        String listName;
        Boolean selectingList = true;

        System.out.println("Type the name of the List to view.\n");
        System.out.println("Type CANCEL to cancel this operation.\n");

        while (selectingList) {

            listName = input.next();
            if (listName.equals("")) {
                System.out.println("Sorry! the name of the media cannot be empty! Please try again.\n");
            } else if (listName.equals("CANCEL")) {
                System.out.println("Cancelled viewing List\n");
                selectingList = false;
            } else {
                selectingList = startListApp(listName);
            }
        }
    }

    /*
    * EFFECTS: start ListApp if list with listName is in listColl
    * */
    private Boolean startListApp(String listName) {
        MediaList listToView = listColl.findMediaListByName(listName);
        if (listToView == null) {
            System.out.println("Sorry! the list with name " + listName + " was not found! Please try again.");
            return true;
        } else {
            new ListApp(listToView);
            return false;
        }
    }

    /*
    * EFFECTS: display listCOll
    * */
    private void displayListColl() {
        String listCollString = "Your lists: ";
        System.out.println("There are currently " + listColl.size() + " lists.");
        Iterator<MediaList> list = listColl.getList().iterator(); //Adapted from Java iterator documentation
        while (list.hasNext()) {
            listCollString += list.next().getName();
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
        System.out.println("Choose a list:\n");
        System.out.println("\t1 -> Create a new list");
        System.out.println("\t2 -> Delete a list");
        System.out.println("\t3 -> View a list");
        System.out.println("\tq -> Quit application");
    }
}
