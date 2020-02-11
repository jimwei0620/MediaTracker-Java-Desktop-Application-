package model;

import exceptions.EmptyStringException;
import exceptions.NullDataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//List of MediaItem with a name
public class MediaList {

    protected ArrayList<MediaItem> mediaItemList; //List that holds all the MediaItem
    protected String listName; //Name of the list

    /*
     * MODIFIES: this
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to listName. throws
     * EmptyStringException if listName is empty.
     * */
    public MediaList(String listName) throws EmptyStringException {
        if (listName.isEmpty()) {
            throw new EmptyStringException();
        }
        this.listName = listName;
        this.mediaItemList = new ArrayList<>();
    }

    public String getName() {
        return this.listName;
    }

    // MODIFIES: this
    // EFFECTS: set the name of the list to newName. Throws EmptyString Exception if
    // newName is empty
    public void setName(String newName) throws EmptyStringException {
        if (newName.isEmpty()) {
            throw new EmptyStringException();
        }
        this.listName = newName;
    }

    public ArrayList<MediaItem> getList() {
        return this.mediaItemList;
    }

    /*
    * MODIFIES: this
    * EFFECTS: media is added to the mediaList
    * */
    public void addMedia(MediaItem media) {
        mediaItemList.add(media);
    }

    /*
    * MODIFIES: this
    * EFFECTS: remove media from mediaList
    * */
    public void removeMedia(MediaItem media) {
        mediaItemList.remove(media);
    }

    /*
    * EFFECTS: Search media with mediaName in the mediaList returns null if media with mediaName is not found,
    * else return the MediaItem with the name. Throws EmptyStringException if mediaName is empty
    * */
    public MediaItem getMediaItemByName(String mediaName) throws EmptyStringException, NullDataException {
        if (mediaName.isEmpty()) {
            throw new EmptyStringException();
        }
        for (MediaItem item: mediaItemList) {
            if (item.getName().equals(mediaName)) {
                return item;
            }
        }
        throw new NullDataException();
    }

    // EFFECTS: save details of the MediaList and return it as a JsonObject
    public JSONObject save() {
        JSONObject mediaList = new JSONObject();
        mediaList.put("listName", this.listName);
        JSONArray arrayOfMediaItem = new JSONArray();
        for (MediaItem item: mediaItemList) {
            JSONObject mediaItemObject = item.save();
            arrayOfMediaItem.put(mediaItemObject);
        }
        mediaList.put("mediaItemList", arrayOfMediaItem);
        return mediaList;
    }
}
