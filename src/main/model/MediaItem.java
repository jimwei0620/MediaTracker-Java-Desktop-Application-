package model;

import org.json.JSONObject;


// Represents a media(Movies, tv show...) item with a name and status (whether it has been seen or not)
public class MediaItem {

    private String mediaName; //Name of the Media
    private Boolean watched; //State of the media; if it has been watched

    /*
    * REQUIRES: None empty mediaName
    * MODIFIES: this
    * EFFECTS: Creates a MediaItem with name mediaName and watch status false
    * */
    public MediaItem(String mediaName) {
        this.mediaName = mediaName;
        this.watched = false;
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

    public JSONObject save() {
        JSONObject mediaItem = new JSONObject();
        mediaItem.put("itemName", mediaName);
        mediaItem.put("Status", watched.toString());
        return mediaItem;
    }
}
