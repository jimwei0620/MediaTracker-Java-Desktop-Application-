package ui;

import exceptions.EmptyStringException;
import exceptions.InvalidRatingException;
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
        String type;
        Float rating;

        System.out.println("This is \"" +  mediaItem.getName() + "\"!");
        System.out.println(mediaItem.getName() + " is currently " + mediaItem.getWatchStatus() + ".");
        type = mediaItem.getType();
        rating = mediaItem.getRating();
        System.out.println("Rating: " + rating + " Type: " + type + "\nComments: " + mediaItem.getComment() + "\n");
        System.out.println("Choose an action:");
        System.out.println("\t1 -> change item status");
        System.out.println("\t2 -> change item name");
        System.out.println("\t3 -> change item rating");
        System.out.println("\t4 -> edit item comment");
        System.out.println("\t5 -> change item type");
        System.out.println("\tq -> exit item\n");
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
            case "3":
                processArgument("changeRating");
                return;
            case "4":
                processArgument("editComment");
                return;
            case "5":
                processArgument("changeType");
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
            if (textCommand.equals("changeType")) {
                displayMediaItemTypeChoices();
            }
            System.out.println("Type the name of the media to " + textCommand + ".");
            System.out.println("Type \"CANCEL\" to cancel this operation.\n");
            argument = input.nextLine();
            if (argument.equals("CANCEL")) {
                System.out.println("Operation was cancelled.\n");
                processingArgument = false;
            } else {
                try {
                    processingArgument = !processOperation(textCommand, argument);
                } catch (EmptyStringException e) {
                    System.out.println("Please enter something!");
                }
            }
        }
    }

    /*
     * EFFECTS: process operation specified with argument. returns true if operation is successful else return false
     *  */
    private Boolean processOperation(String op, String argument) throws EmptyStringException {
        switch (op) {
            case "changeName":
                return changeMediaItemName(argument);
            case "changeStatus":
                return changeMediaItemStatus(argument);
            case "changeRating":
                return changeMediaItemRating(argument);
            case "changeType":
                return changeMediaItemType(argument);
            case "editComment":
                return editMediaItemComment(argument);
            default:
                System.out.println("Internal Error!");
                return false;
        }
    }

    private Boolean editMediaItemComment(String argument) {
        mediaItem.setComment(argument);
        return true;
    }

    // EFFECTS: display choices for mediaItem types
    private void displayMediaItemTypeChoices() {
        System.out.println("Choose a type:");
        System.out.println("\t1 -> MOVIE");
        System.out.println("\t2 -> TV SHOW");
        System.out.println("\t3 -> ANIMATION");
        System.out.println("\t4 -> MANGA");
        System.out.println("\t5 -> COMIC");
        System.out.println("\t6 -> OTHER");
    }

    // EFFECTS: change media type based on argument
    private Boolean changeMediaItemType(String argument) {
        switch (argument) {
            case "1":
                mediaItem.setType("MOVIE");
                return true;
            case "2":
                mediaItem.setType("TV SHOW");
                return true;
            case "3":
                mediaItem.setType("ANIMATION");
                return true;
            case "4":
                mediaItem.setType("MANGA");
                return true;
            case "5":
                mediaItem.setType("COMIC");
                return true;
            case "6":
                mediaItem.setType("OTHER");
                return true;
            default:
                System.out.println("That choice is invalid. Please try again!");
                return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Change the rating of the MediaItem. Catches NumberFormatException and InvalidRatingException
    // returns true if successful, else false
    private Boolean changeMediaItemRating(String argument) {
        try {
            Float rating = Float.parseFloat(argument);
            mediaItem.setRating(rating);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("You did not enter a valid rating! Please try again!");
            return false;
        } catch (InvalidRatingException e) {
            System.out.println("Please provide a number from 0 - 10");
            return false;
        }
    }


    // EFFECTS: displays choices of statuses to the console
    private void displayStatusChoices() {
        System.out.println("Choose a status:");
        System.out.println("\t1 -> WATCHED");
        System.out.println("\t2 -> NOT WATCHED");
        System.out.println("\t3 -> IN PROGRESS");
    }

    /*
    * MODIFIES: this
    * EFFECTS: change the watch status of mediaItem, returns true if argument is valid, else return false
    * */
    private Boolean changeMediaItemStatus(String argument) throws EmptyStringException {
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
        try {
            mediaItem.setName(argument);
            System.out.println("\"" + mediaItem.getName() + "\" was successfully set as the new name!\n");
            return true;
        } catch (EmptyStringException e) {
            System.out.println("The name of the media cannot be empty! Please try again!");
            return false;
        }
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
