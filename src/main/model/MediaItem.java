package model;

import exceptions.EmptyStringException;
import org.json.JSONObject;


// Represents a media(Movies, tv show...) item with a name and status (whether it has been seen or not)
public class MediaItem {

    private String mediaName; //Name of the Media
    private String status; //State of the media; if it has been watched

    /*
    * MODIFIES: this
    * EFFECTS: Creates a MediaItem with name mediaName and watch status false. Throws EmptyStringException
    * if mediaName is empty.
    * */
    public MediaItem(String mediaName) throws EmptyStringException {
        if (mediaName.isEmpty()) {
            throw new EmptyStringException();
        }
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


    // EFFECTS: save the details of the MediaItem and return it as a JSONObject
    public JSONObject save() {
        JSONObject mediaItem = new JSONObject();
        mediaItem.put("mediaName", mediaName);
        mediaItem.put("status", status);
        return mediaItem;
    }
}
