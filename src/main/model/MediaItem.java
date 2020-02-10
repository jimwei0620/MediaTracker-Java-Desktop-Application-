package model;

import org.json.JSONObject;


// Represents a media(Movies, tv show...) item with a name and status (whether it has been seen or not)
public class MediaItem {

    private String mediaName; //Name of the Media
    private String status; //State of the media; if it has been watched

    /*
    * REQUIRES: None empty mediaName
    * MODIFIES: this
    * EFFECTS: Creates a MediaItem with name mediaName and watch status false
    * */
    public MediaItem(String mediaName) {
        this.mediaName = mediaName;
        status = "NOT WATCHED";
    }

    public String getName() {
        return this.mediaName;
    }

    public void setName(String mediaName) {
        this.mediaName = mediaName;
    }

    public void setWatchStatus(String watched) {
        this.status = watched;
    }

    public String getWatchStatus() {
        return  this.status;
    }

    public JSONObject save() {
        JSONObject mediaItem = new JSONObject();
        mediaItem.put("mediaName", mediaName);
        mediaItem.put("status", status);
        return mediaItem;
    }
}
