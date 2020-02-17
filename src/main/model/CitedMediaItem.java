package model;

import org.json.JSONObject;

// Represent a (movie, animation...) cited from outside sources like i m d b will develop after http request is
// implemented
public class CitedMediaItem extends MediaItem {

    // MODIFIES: this
    //EFFECTS: initialize itemDetails with details for info from cited sources
    public CitedMediaItem() {
        super();
        super.itemDetails.put("Title", "");
        super.itemDetails.put("Year", "");
        super.itemDetails.put("Rated", "");
        super.itemDetails.put("Runtime", "");
        super.itemDetails.put("Genre", "");
        super.itemDetails.put("Released", "");
        super.itemDetails.put("Plot", "");
        super.itemDetails.put("Poster", "");
        super.itemDetails.put("Rating", "");
        super.itemDetails.put("Votes", "");
        super.itemDetails.put("Type", "");
        super.itemDetails.put("Status", "");
        super.itemDetails.put("UserRating", "");
        super.itemDetails.put("UserComments", "");
    }

    @Override
    public JSONObject save() {
        return null;
    }
}
