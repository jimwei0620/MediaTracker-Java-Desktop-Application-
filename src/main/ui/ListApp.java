package ui;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.org.apache.xpath.internal.operations.Bool;
import model.MediaItem;
import model.MediaList;

import java.util.Scanner;

public class ListApp {
    private Scanner input;
    private MediaList mediaList;

    // EFFECTS: Runs the interface for a MediaList
    public ListApp(MediaList mediaList) {
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
        System.out.println("This is the " +  mediaList.getName() + "!\n");
        while (listIsRunning) {
            displayListMenuOptions();
            listCommand = input.next();

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
                displayList();
                return;
            case "2":
                addNewMediaToList();
                return;
            case "3":
                deleteMediaFromList();
                return;
            case "4":
                changeMediaListName();
                return;
            case "5":
                viewMediaItem();
                return;
            default:
                System.out.println("Sorry...that command is invalid. Please try again!\n");

        }
    }

    /*
    * EFFECTS: display the mediaList to the user
    * */
    private void displayList() {
        String listToDisplay = "";
        for (MediaItem item : mediaList.getList()) {
            listToDisplay += item.getName() + " ";
        }
        if (listToDisplay.equals("")) {
            System.out.println("List is currently empty.\n");
        } else {
            System.out.println(listToDisplay);

        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: adds a new MediaItem(user created) to list
    * */
    private void addNewMediaToList() {
        MediaItem newMedia;
        String newMediaName;
        Boolean addingNewMedia = true;

        System.out.println("Type the name of the media to add.\n");
        System.out.println("Type CANCEL to cancel this operation.\n");

        while (addingNewMedia) {

            newMediaName = input.next();
            if (newMediaName.equals("")) {
                System.out.println("Sorry! the name of the media cannot be empty! Please try again.\n");
                System.out.println("Type CANCEL to cancel the operation.\n");
            } else if (newMediaName.equals("CANCEL")) {
                System.out.println("Cancelled adding new media to " + mediaList.getName());
                addingNewMedia = false;
            } else {
                newMedia = new MediaItem(newMediaName);
                mediaList.addMedia(newMedia);
                addingNewMedia = false;
                System.out.println(newMediaName + " was successfully added to " + mediaList.getName() + "\n");
                System.out.println(newMediaName + " is currently unwatched.\n");
            }
        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: delete a MediaItem from mediaList
    * */
    private void deleteMediaFromList() {
        String mediaToDelete;
        Boolean deletingMedia = true;
        MediaItem mediaFound;

        System.out.println("Type the name of the media to delete.\n");
        System.out.println("Type CANCEL to cancel the operation.\n");

        while (deletingMedia) {
            mediaToDelete = input.next();
            mediaFound = mediaList.getMediaItemByName(mediaToDelete);
            if (mediaToDelete.equals("CANCEL")) {
                deletingMedia = false;
                System.out.println("Cancelled deleting media.");
            } else if (mediaFound == null) {
                System.out.println("Sorry, media was not found. Please try again!\n");
                System.out.println("Type CANCEL to cancel the operation.\n");
            } else {
                mediaList.removeMedia(mediaFound);
                deletingMedia = false;
                System.out.println(mediaToDelete + " has been successfully deleted from " + mediaList.getName() + "\n");
            }
        }
    }

    /*
    * MODIFIES: this
    * EFFECTS: change the name of the mediaList
    * */
    private void changeMediaListName() {
        String newName;
        Boolean changingName = true;


        System.out.println("Type a new name for the list!\n");
        System.out.println("Type CANCEL to cancel the operation.\n");

        while (changingName) {
            newName = input.next();
            if (newName.equals("")) {
                System.out.println("Sorry!, the name of the list cannot be empty! Please try again.\n");
                System.out.println("Type CANCEL to cancel the operation.\n");
            } else if (newName.equals("CANCEL")) {
                changingName = false;
                System.out.println("Cancelled changing the name of the list.\n");
            } else {
                mediaList.setName(newName);
                System.out.println(newName + " has successfully been set to the new name of the list!\n");
                changingName = false;
            }
        }
    }

    /*
    * EFFECTS: open the MediaItemApp for a user specific media(by name)
    * */
    private void viewMediaItem() {
        String mediaToView;
        Boolean getMediaName = true;
        MediaItem mediaFound;


        System.out.println("Type the name of the media to view.\n");
        System.out.println("Type CANCEL to cancel the operation.\n");

        while (getMediaName) {
            mediaToView = input.next();
            mediaFound = mediaList.getMediaItemByName(mediaToView);
            if (mediaFound == null) {
                System.out.println("Sorry media with name " + mediaToView + "was not found. Please try again.\n");
                System.out.println("Type CANCEL to cancel the operation.\n");
            } else if (mediaToView.equals("CANCEL")) {
                System.out.println("Cancelled viewing media item.\n");
                getMediaName = false;
            } else {
                getMediaName = false;
                new MediaItemApp(mediaFound);
            }
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
        System.out.println("Choose an action:\n");
        System.out.println("\t1 -> view list\n");
        System.out.println("\t2 -> add a media to list\n");
        System.out.println("\t3 -> delete a media from list\n");
        System.out.println("\t4 -> Change the name of the list\n");
        System.out.println("\t5 -> View a media Item\n");
        System.out.println("\tq -> exit list\n");
    }
}
