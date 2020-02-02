package model;

// Represents a media(Movies, tv show...) item with a name and status (whether it has been seen or not)
public class MediaItem {

    private String mediaName; //Name of the Media
    private Boolean watched; //State of the media; if it has been watched

    /*
    * REQUIRES: None empty mediaName
    * MODIFIES: this
    * EFFECTS: Creates a MediaItem with name mediaName
    * */
    public MediaItem(String mediaName, Boolean watched) {
        this.mediaName = mediaName;
        this.watched = watched;
    }

    public String getName() {
        return this.mediaName;
    }

    public void setName(String mediaName) {
        this.mediaName = mediaName;
    }

    public void setWatchStatus(Boolean watched) {
        this.watched = watched;
    }

    public Boolean getWatchStatus() {
        return  this.watched;
    }
}
