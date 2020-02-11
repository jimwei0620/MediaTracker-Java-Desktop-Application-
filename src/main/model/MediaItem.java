package model;

import exceptions.*;
import org.json.JSONObject;


// Represents a media(Movies, tv show...) item with a name and status (whether it has been seen or not)
public class MediaItem {

    private String mediaName; //Name of the Media
    private String status; //State of the media; if it has been
    private Float rating;
    private String comments;
    private String type;

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
        comments = "";
    }

    public String getName() {
        return this.mediaName;
    }

    public void setName(String mediaName) throws EmptyStringException {
        if (mediaName.isEmpty()) {
            throw  new EmptyStringException();
        }
        this.mediaName = mediaName;
    }

    public void setWatchStatus(String watched) throws EmptyStringException {
        if (watched.isEmpty()) {
            throw new EmptyStringException();
        }
        this.status = watched;
    }

    public String getWatchStatus() {
        return  this.status;
    }

    // MODIFIES: this
    // EFFECTS: set rating of the MediaItem. Throws InvalidRating Exception if rating is not 0 - 10
    public void setRating(Float rating) throws InvalidRatingException {
        if (rating > 10 || rating < 0) {
            throw new InvalidRatingException();
        }
        this.rating = rating;
    }

    public Float getRating() {
        return this.rating;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setComment(String comment) {
        this.comments = comment;
    }

    public String getComment() {
        return this.comments;
    }

    // EFFECTS: save the details of the MediaItem and return it as a JSONObject
    public JSONObject save() {
        JSONObject mediaItem = new JSONObject();
        mediaItem.put("mediaName", mediaName);
        mediaItem.put("status", status);
        mediaItem.put("comments", comments);
        mediaItem.put("type", type);
        mediaItem.put("rating", rating);
        return mediaItem;
    }
}
