package ui;


import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import exceptions.ItemNotFoundException;
import exceptions.NullDataException;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


import persistence.ReadUserItem;
import persistence.Reader;
import persistence.Writer;

import javax.management.openmbean.KeyAlreadyExistsException;

//Media Tracker application Modeled after TellerApp example given for now
public class TrackerApp {
    private static final String LIST_FILE = "./data/listFile.json";
    private static final String TAG_FILE = "./data/tagFile.json";
    private static final String ITEM_FILE = "./data/userItemFile.json";

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
                saveProgram();
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
        System.out.println("Reading data from files......");
        ArrayList<ReadUserItem> userItemRead = Reader.readItemFile(ITEM_FILE);
        ArrayList<Tag> tagRead = Reader.readTagFile(TAG_FILE);
        ArrayList<MediaList> listRead = Reader.readListFile(LIST_FILE);
        System.out.println("Loading tags......");
        processTagData(tagRead);
        System.out.println("Loading lists......");
        processListData(listRead);
        System.out.println("Loading items......");
        processUserItemData(userItemRead);
        System.out.println("Data has been loaded.");
    }

    private void processTagData(ArrayList<Tag> tagRead) {
        if (tagRead != null) {
            for (Tag tag: tagRead) {
                listColl.addNewTag(tag);
            }
        } else {
            System.out.println("There are no tags!");
        }

    }

    private void processListData(ArrayList<MediaList> listRead) {
        if (listRead != null) {
            for (MediaList list: listRead) {
                listColl.addNewList(list);
            }
        } else {
            System.out.println("There are no lists");
        }
    }

    private void processUserItemData(ArrayList<ReadUserItem> userItemRead) {
        if (userItemRead != null) {
            ArrayList<UserMediaItem> userMediaItems = processAllItems(userItemRead);
            listColl.getAllUserMediaItems().addAll(userMediaItems);
            loadToLists(userMediaItems);
            loadToTags(userMediaItems);

        } else {
            System.out.println("There are no items!");
        }
    }

    private ArrayList<UserMediaItem> processAllItems(ArrayList<ReadUserItem> itemRead) {
        ArrayList<UserMediaItem> userMediaItemArrayList = new ArrayList<>();
        for (ReadUserItem item: itemRead) {
            UserMediaItem userMediaItem = new UserMediaItem(item.getTitle());
            try {
                userMediaItem.setItemInfo("Status", item.getStatus());
                userMediaItem.setItemInfo("UserRating", item.getUserRating());
                userMediaItem.setItemInfo("Type", item.getType());
                userMediaItem.setItemInfo("UserComments", item.getUserComments());
                userMediaItemArrayList.add(userMediaItem);
                userMediaItem.getMetaDataOfType("List").addAll(item.getMetaDataList());
                userMediaItem.getMetaDataOfType("Tag").addAll(item.getMetaDataTag());
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
                System.out.println("Internal Error");
            }
        }
        return userMediaItemArrayList;
    }

    private void loadToLists(ArrayList<UserMediaItem> userMediaItems) {
        for (MediaList list: listColl.allActiveLists()) {
            for (UserMediaItem item: userMediaItems) {
                try {
                    if (item.containMetaDataOf("List", list.getName())) {
                        try {
                            listColl.getListOfMedia(list).add(item);
                        } catch (ItemNotFoundException e) {
                            System.out.println("Internal Error");
                        }
                    }
                } catch (ItemNotFoundException e) {
                    System.out.println("Internal Error");
                }
            }
        }
    }

    private void loadToTags(ArrayList<UserMediaItem> userMediaItems) {
        for (Tag tag: listColl.getAllActiveTags()) {
            for (UserMediaItem item: userMediaItems) {
                try {
                    if (item.containMetaDataOf("List", tag.getTagName())) {
                        try {
                            listColl.getListOfMediaWithTag(tag).add(item);
                        } catch (ItemNotFoundException e) {
                            System.out.println("Internal Error");
                        }
                    }
                } catch (ItemNotFoundException e) {
                    System.out.println("Internal Error");
                }
            }
        }
    }

    // EFFECTS: save state of listColl to LISTS_FILE. Modeled after TellerApp
    private void saveProgram() {
        try {
            Writer writer = new Writer(new File(LIST_FILE), new File(TAG_FILE), new File(ITEM_FILE));
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
            listColl.addNewList(newMediaList);
            System.out.println("\"" + argument + "\" was successfully added.\n");
            return true;
        } catch (EmptyStringException e) {
            System.out.println("Name of the new list cannot be empty! Try again!");
            return false;
        } catch (KeyAlreadyExistsException e) {
            System.out.println("There is already a list with that name!");
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
            MediaList mediaListToDelete = listColl.getMediaListByName(argument);
            listColl.removeList(mediaListToDelete);
            System.out.println("\"" + argument + "\" was successfully deleted.\n");
            return true;
        } catch (ItemNotFoundException e) {
            System.out.println("List was not found.");
            return false;
        }
    }

    /*
    * EFFECTS: start ListApp if list with listName is in listColl and return true, else return false;
    * Catches EmptyStringException
    * */
    private Boolean startListApp(String listName) {
        try {
            MediaList listToView = listColl.getMediaListByName(listName);
            new ListUI(listToView, listColl.getListOfMedia(listToView), listColl);
            return true;
        } catch (ItemNotFoundException e) {
            System.out.println("List was not found");
            return false;
        }
    }

    /*
    * EFFECTS: display listCOll
    * */
    private void displayListColl() {
        String listCollString = "Your lists: ";
        System.out.println("There are currently \"" + listColl.numOfLists() + "\" lists.");
        Iterator<MediaList> list = listColl.allActiveLists().iterator(); //Adapted from Java iterator documentation
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
