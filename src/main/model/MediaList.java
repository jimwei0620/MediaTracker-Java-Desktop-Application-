package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//List of MediaItem with a name
public class MediaList {

    protected ArrayList<MediaItem> mediaItemList; //List that holds all the MediaItem
    protected String listName; //Name of the list

    /*
     * REQUIRES: listName can not be empty String
     * MODIFIES: this
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to Listname
     * */
    public MediaList(String listName) {
        this.listName = listName;
        this.mediaItemList = new ArrayList<>();
    }

    public String getName() {
        return this.listName;
    }

    public void setName(String newName) {
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
    * REQUIRES: none empty name
    * EFFECTS: Search media with mediaName in the mediaList returns null if media with mediaName is not found,
    * else return the MediaItem with the name
    * */
    public MediaItem getMediaItemByName(String mediaName) {
        for (MediaItem item: mediaItemList) {
            if (item.getName().equals(mediaName)) {
                return item;
            }
        }
        return null;
    }

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
