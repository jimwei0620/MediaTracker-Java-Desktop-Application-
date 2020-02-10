package ui;

import model.MediaItem;

import java.util.Scanner;

public class MediaItemUI {
    private  MediaItem mediaItem;
    private Scanner input;

    /*
    * EFFECTS: runs the MediaItemApp
    * */
    public MediaItemUI(MediaItem item) {
        runMediaItemApp(item);
    }

    /*
    * MODIFIES: this
    * EFFECTS: process user input for MediaItemApp
    * */
    private void runMediaItemApp(MediaItem item) {
        Boolean mediaItemIsRunning = true;
        String mediaItemCommand;

        initMediaItemApp(item);

        while (mediaItemIsRunning) {
            displayMediaItemMenuOptions();
            mediaItemCommand = input.nextLine();
            if (mediaItemCommand.equals("q")) {
                mediaItemIsRunning = false;
            } else {
                processMediaItemCommand(mediaItemCommand);
            }
        }
        System.out.println("Exiting \"" + mediaItem.getName() + "\". Back to List!\n");
    }

    /*
    * EFFECTS: print out menu options
    * */
    private void displayMediaItemMenuOptions() {
        System.out.println("This is \"" +  mediaItem.getName() + "\"!");
        System.out.println(mediaItem.getName() + " is currently " + mediaItem.getWatchStatus() + ".");
        System.out.println("Choose an action:");
        System.out.println("\t1 -> change item status");
        System.out.println("\t2 -> change item name");
        System.out.println("\tq -> exit list\n");
    }

    /*
    * EFFECTS: process user commands
    * */
    private void processMediaItemCommand(String mediaItemCommand) {
        switch (mediaItemCommand) {
            case "1":
                processArgument("changeStatus");
                return;
            case "2":
                processArgument("changeName");
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
            if (textCommand.equals("changeStatus")) {
                displayStatusChoices();
            }
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
     * EFFECTS: process operation specified with argument. returns true if operation is successful else return false
     *  */
    private Boolean processOperation(String op, String argument) {
        switch (op) {
            case "changeName":
                return changeMediaItemName(argument);
            case "changeStatus":
                return changeMediaItemStatus(argument);
            default:
                System.out.println("Internal Error!");
                return false;
        }
    }

    // EFFECTS: displays choices of statuses to the console
    private void displayStatusChoices() {
        System.out.println("Choose an action:");
        System.out.println("\t1 -> WATCHED");
        System.out.println("\t2 -> NOT WATCHED");
        System.out.println("\t3 -> IN PROGRESS");
    }

    /*
    * MODIFIES: this
    * EFFECTS: change the watch status of mediaItem, returns true if argument is valid, else return false
    * */
    private Boolean changeMediaItemStatus(String argument) {
        switch (argument) {
            case "1":
                mediaItem.setWatchStatus("WATCHED");
                displayMediaItemStatus();
                return true;
            case "2":
                mediaItem.setWatchStatus("NOT WATCHED");
                displayMediaItemStatus();
                return true;
            case "3":
                mediaItem.setWatchStatus("IN PROGRESS");
                displayMediaItemStatus();
                return true;
            default:
                System.out.println("That command is invalid!\n");
                return false;
        }
    }

    /*
    * MODIFIES: this
    * EFFECT: change the name of mediaItem. Return true
    * */
    private Boolean changeMediaItemName(String argument) {
        mediaItem.setName(argument);
        System.out.println("\"" + mediaItem.getName() + "\" was successfully set as the new name!\n");
        return true;
    }

    /*
    * EFFECT: prints the watch status of mediaItem
    * */
    private void displayMediaItemStatus() {
        System.out.println("\"" + mediaItem.getName() + "\" is " + mediaItem.getWatchStatus() + "\n");
    }

    /*
    * MODIFIES: this
    * EFFECTS: initializes mediaItem and inputs
    * */
    private void initMediaItemApp(MediaItem item) {
        this.mediaItem = item;
        input = new Scanner(System.in);
    }
}
