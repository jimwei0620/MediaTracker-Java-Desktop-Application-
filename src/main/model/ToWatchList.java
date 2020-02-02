package model;

import java.util.List;

public class ToWatchList extends  MediaList {

    /*
     * REQUIRES: listName can not be empty
     * MODIFIES: this
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to Listname
     * */
    public ToWatchList(String listName) {
        super(listName);
    }
}
