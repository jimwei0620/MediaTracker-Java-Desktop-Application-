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
        System.out.println("This is the \"" +  mediaItem.getName() + "\"!");
        System.out.println("Choose an action:");
        System.out.println("\t1 -> view item watch status");
        System.out.println("\t2 -> change item status");
        System.out.println("\t3 -> change item name");
        System.out.println("\tq -> exit list\n");
    }

    /*
    * EFFECTS: process user commands
    * */
    private void processMediaItemCommand(String mediaItemCommand) {
        switch (mediaItemCommand) {
            case "1":
                displayMediaItemStatus();
                return;
            case "2":
                changeMediaItemStatus();
                return;
            case "3":
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
            default:
                System.out.println("Internal Error!");
                return false;
        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: change the watch status of mediaItem
    * */
    private void changeMediaItemStatus() {
        mediaItem.setWatchStatus(!mediaItem.getWatchStatus());
        System.out.println("The watch status of the media item has been changed to...");
        displayMediaItemStatus();
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
        if (mediaItem.getWatchStatus()) {
            System.out.println("\"" + mediaItem.getName() + "\" has been watched\n");
        } else {
            System.out.println("\"" + mediaItem.getName() + "\" has not been Watched\n");
        }
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
