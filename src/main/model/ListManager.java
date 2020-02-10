package model;


import exceptions.EmptyStringException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.SaveAble;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//Represent a manager that control and stores all the lists
public class ListManager implements SaveAble {

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
    * EFFECTS: Find and return MediaList in mediaListColl has the matching name, else return null
    * throws EmptyStringException if String is empty
    * */
    public MediaList findMediaListByName(String name) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException();
        }
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

    @Override
    public void save(FileWriter file) {
        JSONArray arrayOfLists = new JSONArray();
        for (MediaList item: mediaListColl) {
            JSONObject mediaListObject = item.save();
            arrayOfLists.put(mediaListObject);
        }
        arrayOfLists.write(file);
    }
}
