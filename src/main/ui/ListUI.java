package ui;

import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import exceptions.ItemNotFoundException;
import model.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ListUI {
    private Scanner input;
    private MediaList mediaList;
    private ArrayList<MediaItem> listOfItems;
    private ListManager listcoll;

    // EFFECTS: Runs the interface for a MediaList
    public ListUI(MediaList mediaList, ArrayList<MediaItem> listOfItems, ListManager listManager) {
        this.listOfItems = listOfItems;
        this.listcoll = listManager;
        runMediaList(mediaList);
    }

    /*
    * MODIFIES: this
    * EFFECTS: process user input for ListApp
    * */
    private void runMediaList(MediaList list) {
        Boolean listIsRunning = true;
        String listCommand;

        initListApp(list);
        System.out.println("This is the \"" +  mediaList.getName() + "\" list!\n");
        while (listIsRunning) {
            displayList();
            displayListMenuOptions();
            listCommand = input.nextLine();

            if (listCommand.equals("q")) {
                listIsRunning = false;
            } else {
                processListCommand(listCommand);
            }
        }
        System.out.println("Exiting list...back to the Menu!\n");
    }

    /*
     * EFFECTS: processes List commands from the user
     * */
    private void processListCommand(String listCommand) {
        switch (listCommand) {
            case "1":
                processArgument("add");
                return;
            case "2":
                processArgument("delete");
                return;
            case "3":
                processArgument("changeName");
                return;
            case "4":
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
            System.out.println("Type the name of the media to " + textCommand + ".");
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
     * MODIFIES: this
     * EFFECTS: adds a new MediaItem(user created) to list with name argument, returns true if successful. Catches
     * EmptyStringException.
     * */
    private Boolean addNewMediaToList(String argument) {
        UserMediaItem newMedia;
        try {
            newMedia = new UserMediaItem(argument);
            listcoll.addMediaItemToList(mediaList, newMedia);
            System.out.println("\"" + argument + "\" was successfully added to \""
                    + mediaList.getName() + "\".");
            System.out.println("\"" + argument + "\" is currently " + newMedia.getItemInfo("Status") + ".\n");
        } catch (ItemNotFoundException | DataExistAlreadyException e) {
            System.out.println("Internal Error");
            e.printStackTrace();
        } catch (KeyAlreadyExistsException e) {
            System.out.println("There is already an item in the list with that name!");
        }
        return true;
    }

    /*
     * EFFECTS: process operation specified with argument. returns true if operation is successful else return false
     *  */
    private Boolean processOperation(String op, String argument) {
        switch (op) {
            case "add":
                return addNewMediaToList(argument);
            case "delete":
                return deleteMediaFromList(argument);
            case "changeName":
                return changeMediaListName(argument);
            case "select":
                return viewMediaItem(argument);
            default:
                System.out.println("Internal Error!");
                return false;
        }
    }

    /*
    * EFFECTS: display the mediaList to the user
    * */
    private void displayList() {
        String listToDisplay = mediaList.getName() + " : ";
        Iterator<MediaItem> list = listOfItems.iterator(); //Adapted from Java iterator documentation
        while (list.hasNext()) {
            try {
                listToDisplay += "\"" + list.next().getItemInfo("Title") + "\"";
            } catch (ItemNotFoundException e) {
                System.out.println("Internal Error");
            }
            if (list.hasNext()) {
                listToDisplay += ", ";
            }
        }
        if (listToDisplay.equals("")) {
            System.out.println("\"" + mediaList.getName() + "\" is currently empty.\n");
        } else {
            System.out.println(listToDisplay);
        }
    }


    /*
    * MODIFIES: this
    * EFFECTS: delete a MediaItem with name argument from the list. Return true if successful, else return false
    * Catches EmptyStringException.
    * */
    private Boolean deleteMediaFromList(String argument) {
        MediaItem mediaFound;
        try {
            mediaFound = listcoll.getMediaItemInListByName(argument, listOfItems);
            listcoll.deleteMediaItemFromList(mediaList, mediaFound);
            System.out.println("\"" + argument + "\""  + " has been successfully deleted from \""
                    + mediaList.getName() + "\n");
            return true;
        } catch (ItemNotFoundException e) {
            System.out.println("Item with that name was not found! try again!");
            return false;
        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: change the name of the mediaList to argument, returns true if successful. Catches EmptyStringException.
    * */
    private Boolean changeMediaListName(String argument) {
        try {
            mediaList.setName(argument);
            System.out.println("\"" + argument + "\"" + " has successfully been set to the new name of the list!\n");
            return true;
        } catch (EmptyStringException e) {
            System.out.println("New name of the list cannot be empty! Try again!");
            return false;
        }
    }

    /*
    * REQUIRES: Non empty String argument
    * EFFECTS: open the MediaItemApp for a user specific media with name argument. Returns true if
    *          MediaItem was found, else return false
    * */
    private Boolean viewMediaItem(String argument) {
        try {
            MediaItem mediaFound;
            mediaFound = listcoll.getMediaItemInListByName(argument, listOfItems);
            new MediaItemUI(mediaFound);
            return true;
        } catch (ItemNotFoundException e) {
            System.out.println("Item with that name was not found. Try again!");
            return  false;
        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: initialize list and input
    * */
    private void initListApp(MediaList list) {
        mediaList = list;
        input = new Scanner(System.in);
    }

    /*
     * EFFECTS: display List menu options to user
     * */
    private void displayListMenuOptions() {
        System.out.println("Choose an action:");
        System.out.println("\t1 -> add a media to list");
        System.out.println("\t2 -> delete a media from list");
        System.out.println("\t3 -> Change the name of the list");
        System.out.println("\t4 -> View a media Item");
        System.out.println("\tq -> exit list\n");
    }
}
