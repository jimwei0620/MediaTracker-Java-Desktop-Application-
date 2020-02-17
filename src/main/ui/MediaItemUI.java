package ui;

import exceptions.EmptyStringException;
import exceptions.InvalidRatingException;
import exceptions.ItemNotFoundException;
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
        try {
            System.out.println("Exiting \"" + mediaItem.getItemInfo("Title") + "\". Back to List!\n");
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error!");
        }
    }

    /*
    * EFFECTS: print out menu options
    * */
    private void displayMediaItemMenuOptions() {
        String type;
        String rating;
        try {
            String title = mediaItem.getItemInfo("Title");
            System.out.println("This is \"" + title + "\"!");
            System.out.println(title + " is currently " + mediaItem.getItemInfo("Status") + ".");
            type = mediaItem.getItemInfo("Type");
            rating = mediaItem.getItemInfo("UserRating");
            System.out.println("Rating: " + rating + " Type: " + type
                    + "\nComments: " + mediaItem.getItemInfo("UserComments") + "\n");
            System.out.println("Choose an action:");
            System.out.println("\t1 -> change item status");
            System.out.println("\t2 -> change item name");
            System.out.println("\t3 -> change item rating");
            System.out.println("\t4 -> edit item comment");
            System.out.println("\t5 -> change item type");
            System.out.println("\tq -> exit item\n");
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error!");
        }
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
            System.out.println("Type the information to " + textCommand + ".");
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
        try {
            mediaItem.setItemInfo("UserComments", argument);
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error!");
        }
        return true;
    }

    // EFFECTS: display choices for mediaItem types
    private void displayMediaItemTypeChoices() {
        System.out.println("Choose a type:");
        System.out.println("\t1 -> MOVIE");
        System.out.println("\t2 -> TV SHOW");
        System.out.println("\t3 -> ANIMATION");
        System.out.println("\t4 -> OTHER");
    }

    // EFFECTS: change media type based on argument
    private Boolean changeMediaItemType(String argument) {
        try {
            switch (argument) {
                case "1":
                    mediaItem.setItemInfo("Type", "MOVIE");
                    return true;
                case "2":
                    mediaItem.setItemInfo("Type", "TV SHOW");
                    return true;
                case "3":
                    mediaItem.setItemInfo("Type", "ANIMATION");
                    return true;
                case "4":
                    mediaItem.setItemInfo("Type", "OTHER");
                    return true;
                default:
                    System.out.println("That choice is invalid. Please try again!");
                    return false;
            }
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error");
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Change the rating of the MediaItem. Catches NumberFormatException and InvalidRatingException
    // returns true if successful, else false
    private Boolean changeMediaItemRating(String argument) {
        try {
            Float checkNum = Float.parseFloat(argument);
            mediaItem.setItemInfo("UserRating", argument);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("You did not enter a valid rating! Please try again!");
            return false;
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error");
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
    private Boolean changeMediaItemStatus(String argument) {
        try {
            switch (argument) {
                case "1":
                    mediaItem.setItemInfo("Status", "WATCHED");
                    displayMediaItemStatus();
                    return true;
                case "2":
                    mediaItem.setItemInfo("Status", "NOT WATCHED");
                    displayMediaItemStatus();
                    return true;
                case "3":
                    mediaItem.setItemInfo("Status", "IN PROGRESS");
                    displayMediaItemStatus();
                    return true;
                default:
                    System.out.println("That command is invalid!\n");
                    return false;
            }
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error");
            return false;
        }
    }

    /*
    * MODIFIES: this
    * EFFECT: change the name of mediaItem. Return true
    * */
    private Boolean changeMediaItemName(String argument) {
        try {
            mediaItem.setItemInfo("Title", argument);
            System.out.println("\"" + mediaItem.getItemInfo("Title") + "\" was successfully set as the new name!\n");
            return true;
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error");
            return false;
        }
    }

    /*
    * EFFECT: prints the watch status of mediaItem
    * */
    private void displayMediaItemStatus() {
        try {
            System.out.println("\"" + mediaItem.getItemInfo("Title") + "\" is "
                    + mediaItem.getItemInfo("Status") + "\n");
        } catch (ItemNotFoundException e) {
            System.out.println("Internal Error");
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
