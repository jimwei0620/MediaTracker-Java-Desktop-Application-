package ui;

import model.MediaItem;

import java.util.Scanner;

public class MediaItemApp {
    private  MediaItem mediaItem;
    private Scanner input;

    /*
    * EFFECTS: runs the MediaItemApp
    * */
    public MediaItemApp(MediaItem item) {
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
            mediaItemCommand = input.next();
            if (mediaItemCommand.equals("q")) {
                mediaItemIsRunning = false;
            } else {
                processMediaItemCommand(mediaItemCommand);
            }
        }
        System.out.println("Exiting " + mediaItem.getName() + ". Back to List!\n");
    }

    /*
    * EFFECTS: print out menu options
    * */
    private void displayMediaItemMenuOptions() {
        System.out.println("This is the " +  mediaItem.getName() + "!\n");
        System.out.println("Choose an action:\n");
        System.out.println("\t1 -> view item watch status\n");
        System.out.println("\t2 -> change item status\n");
        System.out.println("\t3 -> change item name\n");
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
                changeMediaItemName();
                return;
            default:
                System.out.println("Sorry...that command is invalid. Please try again!\n");
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
    * EFFECT: change the name of mediaItem
    * */
    private void changeMediaItemName() {
        String newMediaName;
        Boolean changingItemName = true;

        System.out.println("Type the new name of the media.\n");
        System.out.println("Type CANCEL to cancel this operation.\n");
        while (changingItemName) {
            newMediaName = input.next();
            if (newMediaName.equals("")) {
                System.out.println("Sorry! the name of the media cannot be empty! Please try again.\n");
                System.out.println("Type CANCEL to cancel the operation.\n");
            } else if (newMediaName.equals("CANCEL")) {
                System.out.println("Cancelled adding new media to " + mediaItem.getName());
                changingItemName = false;
            } else {
                mediaItem.setName(newMediaName);
                changingItemName = false;
                System.out.println(mediaItem.getName() + " was successfully set as the new name!\n");
            }
        }
    }

    /*
    * EFFECT: prints the watch status of mediaItem
    * */
    private void displayMediaItemStatus() {
        if (mediaItem.getWatchStatus()) {
            System.out.println("Watched\n");
        } else {
            System.out.println("Not Watched\n");
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
