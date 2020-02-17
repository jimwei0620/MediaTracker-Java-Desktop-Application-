package model;

import exceptions.EmptyStringException;
import exceptions.InvalidRatingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;


// Represents a media(Movies, tv show...) created by user
public class UserMediaItem extends MediaItem {

    // MODIFIES: this
    // EFFECTS: initialize itemDetails with template for the user
    public UserMediaItem(String title) {
        super();
        super.itemDetails.put("Title", title);
        super.itemDetails.put("Status", "");
        super.itemDetails.put("UserRating", "");
        super.itemDetails.put("Type", "");
        super.itemDetails.put("UserComments", "");
    }


    // EFFECTS: save the details of the MediaItem and return it as a JSONObject
    @Override
    public JSONObject save() {
        JSONObject mediaObject = new JSONObject();
        mediaObject.put("title", super.itemDetails.get("Title"));
        mediaObject.put("status", super.itemDetails.get("Status"));
        mediaObject.put("userRating", super.itemDetails.get("UserRating"));
        mediaObject.put("type", super.itemDetails.get("Type"));
        mediaObject.put("userComments", super.itemDetails.get("UserComments"));
        JSONArray listMetaData = new JSONArray();
        JSONArray tagMetaData = new JSONArray();
        for (MetaData data: super.listData) {
            JSONObject dataObject = data.save();
            listMetaData.put(dataObject);
        }
        for (MetaData data: super.tagData) {
            JSONObject dataObject = data.save();
            tagMetaData.put(dataObject);
        }
        mediaObject.put("metaDataList", listMetaData);
        mediaObject.put("metaDataTag", tagMetaData);
        return mediaObject;
    }


}
