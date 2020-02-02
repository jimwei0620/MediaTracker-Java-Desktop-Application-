package model;

//MediaList for watched media
public class WatchedList extends MediaList {

    /*
     * REQUIRES: listName can not be String
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to Listname
     * MODIFIES:this
     * */
    public WatchedList(String listName) {
        super(listName);
    }

    /*
     * MODIFIES: this, mediaItem, ToWatchList
     * EFFECTS: Move the mediaItem to ToWatchList and remove the mediaItem from super.mediaList.
     *          Change watched of mediaItem to false
     * */
    public void setMediaToNotWatched(MediaItem mediaItem, ToWatchList toWatchedList) {
        super.mediaList.remove(mediaItem);
        toWatchedList.addMedia(mediaItem);
        mediaItem.setWatchStatus(false);
    }
}
