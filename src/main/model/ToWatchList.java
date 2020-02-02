package model;

import java.util.List;

//MediaList for things to watch in the future
public class ToWatchList extends  MediaList {

    /*
     * REQUIRES: listName can not be empty String
     * MODIFIES: this
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to listName
     * */
    public ToWatchList(String listName) {
        super(listName);
    }

    /*
    * MODIFIES: this, mediaItem, WatchedList
    * EFFECTS: Move the mediaItem to WatchedList and remove the mediaItem from ToWatchList.
    *          Change watched of mediaItem to true
    * */
    public void setMediaToWatched(MediaItem mediaItem, WatchedList watchedList) {
        super.mediaList.remove(mediaItem);
        watchedList.addMedia(mediaItem);
        mediaItem.setWatchStatus(true);
    }
}
