package model;

public class WatchedList extends MediaList {

    /*
     * REQUIRES: listName can not be empty
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to Listname
     * MODIFIES:this
     * */
    public WatchedList(String listName) {
        super(listName);
    }
}
