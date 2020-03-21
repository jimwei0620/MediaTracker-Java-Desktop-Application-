package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// class that holds all items
public class ItemManager {
    private ArrayList<MediaItem> allMediaItem; //represent all ACTIVE items
    private static ItemManager instance;

    // MODIFIES: this
    // EFFECTS: initialize allMediaItem (empty)
    private ItemManager() {
        allMediaItem = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Get instance of ItemManager
    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    // EFFECTS: return number of active mediaItems
    public int totalNumOfUserItems() {
        return allMediaItem.size();
    }

    // EFFECTS: return list of User defined item
    public ArrayList<MediaItem>  getAllUserMediaItems() {
        return this.allMediaItem;
    }

    // MODIFIES: this
    // EFFECTS: if mediaItem is inactive, remove item from allMediaItems list and return true, else return false.
    public Boolean removeInactiveItem(MediaItem mediaItem) {
        if (!mediaItem.isActive()) {
            allMediaItem.remove(mediaItem);
            return true;
        }
        return false;
    }

    // EFFECTS: save all the items into a JSONObject and return it
    public void saveAllItem(FileWriter itemFile) {
        JSONArray arrayOfItems = new JSONArray();
        for (MediaItem item: allMediaItem) {
            JSONObject mediaListObject = item.save();
            arrayOfItems.put(mediaListObject);
        }
        arrayOfItems.write(itemFile);
    }

}
