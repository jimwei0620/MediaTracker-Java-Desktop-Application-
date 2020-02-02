package model;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

//List of MediaItem with a name
public abstract class MediaList {

    public ArrayList<MediaItem> mediaList; //List that holds all the MediaItem
    public String listName; //Name of the list

    /*
     * REQUIRES: listName can not be empty String
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
    * EFFECTS: media is added to the mediaList
    * */
    public void addMedia(MediaItem media) {
        mediaList.add(media);
    }

    /*
    * MODIFIES: this
    * EFFECTS: remove media from mediaList
    * */
    public void removeMedia(MediaItem media) {
        mediaList.remove(media);
    }
}
