package model;

import java.util.ArrayList;
import java.util.HashSet;

//Represent a manager that control and stores all the lists
public class ListManager {

    private ArrayList<MediaList> mediaListColl;

    /*
    * MODIFIES: this
    * EFFECTS: initializes ListManager with an empty mediaListColl
    * */
    public ListManager() {
        mediaListColl = new ArrayList<>();
    }

    /*
    * MODIFIES: this
    * EFFECTS: Adds mediaList to mediaListColl
    * */
    public void addToColl(MediaList mediaList) {
        mediaListColl.add(mediaList);
    }

    /*
    * EFFECTS: Return the number of MediaLists in mediaListColl
    * */
    public int size() {
        return mediaListColl.size();
    }

    /*
    * MODIFIES: this
    * EFFECTS: Remove mediaList with from mediaListColl
    * */
    public void remove(MediaList mediaList) {
        mediaListColl.remove(mediaList);
    }

    /*
    * REQUIRES: non empty String name
    * EFFECTS: Find and return MediaList in mediaListColl has the matching name, else return null
    * */
    public MediaList findMediaListByName(String name) {
        for (MediaList list: mediaListColl) {
            if (list.getName().equals(name)) {
                return list;
            }
        }
        return null;
    }

    /*
    * EFFECTS: return the mediaListColl aka the list of MediaLists
    * */
    public ArrayList<MediaList> getList() {
        return mediaListColl;
    }
}
