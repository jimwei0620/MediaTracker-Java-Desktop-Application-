package model;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

public abstract class MediaList {
    private ArrayList<MediaItem> mediaList;
    private String listName;

    /*
     * REQUIRES: listName can not be empty
     * MODIFIES: this
     * EFFECTS: Initializes an empty list with type of MediaItems, sets the name of the list to Listname
     * */
    public MediaList(String listName) {
        this.listName = listName;
        this.mediaList = new ArrayList<>();
    }

    public String getName() {
        return this.listName;
    }

    public void setName(String newName) {
        this.listName = newName;
    }

    public ArrayList<MediaItem> getList() {
        return this.mediaList;
    }

    /*
    * MODIFIES: this
    * EFFECTS: media is added to the list
    * */
    public void addMedia(MediaItem media) {
        mediaList.add(media);
    }

}
