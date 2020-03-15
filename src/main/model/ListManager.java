package model;



import exceptions.DataExistAlreadyException;
import exceptions.ItemNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.SaveAble;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Represent a manager that control and stores all the lists
public class ListManager implements SaveAble {
    private Map<MediaList, ArrayList<MediaItem>> listAndItems;
    private Map<Tag, ArrayList<MediaItem>> tagAndItem;
    private ArrayList<MediaItem> allUserMediaItems; //represent all ACTIVE items


     // MODIFIES: this
     // EFFECTS: initializes ListManager with an empty data lists
    public ListManager() {
        listAndItems = new HashMap<>();
        tagAndItem = new HashMap<>();
        allUserMediaItems = new ArrayList<>();
    }

    // EFFECTS: return number of active mediaItems
    public int totalNumOfUserItems() {
        return allUserMediaItems.size();
    }

    // MODIFIES: this
    // EFFECTS: add a new tag
    public void addNewTag(Tag tag) {
        if (tagAndItem.containsKey(tag)) {
            throw new KeyAlreadyExistsException();
        }
        tagAndItem.put(tag, new ArrayList<>());
    }

    // EFFECTS: return number of tags that exist
    public int numOfTags() {
        return tagAndItem.keySet().size();
    }

    // EFFECTS: check if tag is in the list, if not throw ItemNotFoundException
    public void tagInList(Tag tag) throws ItemNotFoundException {
        if (!tagAndItem.containsKey(tag)) {
            throw new ItemNotFoundException();
        }
    }

    // EFFECTS: return all active tags
    public Set<Tag> getAllActiveTags() {
        return this.tagAndItem.keySet();
    }


    // MODIFIES: this
    // EFFECTS: delete a tag
    public void deleteTag(Tag tag) throws ItemNotFoundException {
        tagInList(tag);
        ArrayList<MediaItem> itemsInList = tagAndItem.get(tag);
        for (MediaItem item: itemsInList) {
            item.removeData("Tag", tag.getTagName());
            removeInactiveItem(item);
        }
        tagAndItem.remove(tag);
    }

    // MODIFIES: this
    // EFFECTS: tag a mediaItem
    public void tagItem(Tag tag, MediaItem mediaItem) throws ItemNotFoundException, DataExistAlreadyException {
        tagInList(tag);
        ArrayList<MediaItem> tagList = tagAndItem.get(tag);
        tagList.add(mediaItem);
        mediaItem.updateData("Tag", tag.getTagName());
    }

    // MODIFIES: this
    // EFFECTS: remove tag a mediaItem
    public void removeTag(Tag tag, MediaItem mediaItem) throws ItemNotFoundException {
        tagInList(tag);
        ArrayList<MediaItem> tagList = tagAndItem.get(tag);
        if (tagList.contains(mediaItem)) {
            tagList.remove(mediaItem);
            mediaItem.removeData("Tag", tag.getTagName());
            return;
        }
        throw new ItemNotFoundException();
    }

    // EFFECTS: return the list of MediaItems associated with tag, throw ItemNotFoundException if tag not found.
    public ArrayList<MediaItem> getListOfMediaWithTag(Tag tag) throws ItemNotFoundException {
        tagInList(tag);
        return tagAndItem.get(tag);
    }

    // EFFECTS: return MediaList with name. Throw ItemNotFoundException if not found.
    public MediaList getMediaListByName(String name) throws ItemNotFoundException {
        Set<MediaList> keySet = listAndItems.keySet();
        for (MediaList key: keySet) {
            if (key.getName().equals(name)) {
                return key;
            }
        }
        throw new ItemNotFoundException();
    }

    // EFFECTS: return the List of MediaItem associated with the mediaList, throw ItemNotFoundException if not found.
    public ArrayList<MediaItem> getListOfMedia(MediaList mediaList) throws ItemNotFoundException {
        listAlreadyExists(mediaList);
        return listAndItems.get(mediaList);
    }

    // EFFECTS: return the MediaItem in List with name
    public MediaItem getMediaItemInListByName(String itemName, ArrayList<MediaItem> listOfItems)
            throws ItemNotFoundException {
        for (MediaItem item: listOfItems) {
            if (item.getItemInfo("Title").equals(itemName)) {
                return item;
            }
        }
        throw new ItemNotFoundException();
    }

    // MODIFIES: this
    // EFFECTS: add mediaItem to the list specified
    public void addMediaItemToList(MediaList mediaList, MediaItem mediaItem)
            throws ItemNotFoundException, DataExistAlreadyException, KeyAlreadyExistsException {
        listAlreadyExists(mediaList);
        ArrayList<MediaItem> list = listAndItems.get(mediaList);
        try {
            getMediaItemInListByName(mediaItem.getItemInfo("Title"), list);
        } catch (ItemNotFoundException e) {
            list.add(mediaItem);
            mediaItem.updateData("List", mediaList.getName());
            if (!allUserMediaItems.contains(mediaItem)) {
                allUserMediaItems.add(mediaItem);
            }
            return;
        }
        throw new KeyAlreadyExistsException();
    }

    // MODIFIES: this
    // EFFECTS: add list of mediaItem to list
    public void addListOfItemToList(MediaList mediaList, ArrayList<MediaItem> mediaItems)
            throws DataExistAlreadyException, ItemNotFoundException {
        for (MediaItem item: mediaItems) {
            addMediaItemToList(mediaList, item);
        }
    }

    // MODIFIES: this
    // EFFECTS: delete mediaItem from the list specified
    public void deleteMediaItemFromList(MediaList mediaList, MediaItem mediaItem) throws ItemNotFoundException {
        listAlreadyExists(mediaList);
        ArrayList<MediaItem> list = listAndItems.get(mediaList);
        list.remove(mediaItem);
        mediaItem.removeData("List", mediaList.getName());
        removeInactiveItem(mediaItem);
    }

    // MODIFIES: this
    // EFFECTS: add a new key to listAndItem and associate it with a new empty list
    public void addNewList(MediaList mediaList) {
        if (listAndItems.containsKey(mediaList)) {
            throw new KeyAlreadyExistsException();
        }
        listAndItems.put(mediaList, new ArrayList<>());
    }

    // EFFECTS: return the tagToItem Hashmap
    public Map<Tag, ArrayList<MediaItem>> getTagSet() {
        return this.tagAndItem;
    }

    // EFFECTS: check if mediaList exists, throw ItemNotFoundException if not
    public void listAlreadyExists(MediaList mediaList) throws ItemNotFoundException {
        if (!listAndItems.containsKey(mediaList)) {
            throw new ItemNotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a key(list) from listAndItem and its associating list
    public void removeList(MediaList mediaList) throws ItemNotFoundException {
        listAlreadyExists(mediaList);
        ArrayList<MediaItem> itemsInList = listAndItems.get(mediaList);
        for (MediaItem item: itemsInList) {
            item.removeData("List", mediaList.getName());
            removeInactiveItem(item);
        }
        listAndItems.remove(mediaList);
    }

    // MODIFIES: this
    // EFFECTS: Return the number of lists
    public int numOfLists() {
        return listAndItems.keySet().size();
    }

    // EFFECTS: return set of all keys to lists
    public Set<MediaList> allActiveLists() {
        return listAndItems.keySet();
    }

    // MODIFIES: this
    // EFFECTS: if mediaItem is inactive, remove item from allMediaItems list and return true, else return false.
    public Boolean removeInactiveItem(MediaItem mediaItem) {
        if (!mediaItem.isActive()) {
            allUserMediaItems.remove(mediaItem);
            return true;
        }
        return false;
    }

    // EFFECTS: Save all the tags into a JSONObject and return it
    public void saveTag(FileWriter tagFile) {
        JSONArray arrayOfTags = new JSONArray();
        for (Tag tag: tagAndItem.keySet()) {
            JSONObject tagObject = tag.save();
            arrayOfTags.put(tagObject);
        }
        arrayOfTags.write(tagFile);
    }


    // EFFECTS: save all the items into a JSONObject and return it
    public void saveItem(FileWriter itemFile) {
        JSONArray arrayOfItems = new JSONArray();
        for (MediaItem item: allUserMediaItems) {
            JSONObject mediaListObject = item.save();
            arrayOfItems.put(mediaListObject);
        }
        arrayOfItems.write(itemFile);
    }

    // EFFECTS: save all the lists into a JSONObject and return it
    public void saveList(FileWriter listFile) {
        JSONArray arrayOfLists = new JSONArray();
        for (MediaList list: listAndItems.keySet()) {
            JSONObject listObject = list.save();
            arrayOfLists.put(listObject);
        }
        arrayOfLists.write(listFile);
    }

    // EFFECTS: return list of User defined item
    public ArrayList<MediaItem>  getAllUserMediaItems() {
        return this.allUserMediaItems;
    }

    @Override
    public void save(FileWriter listFile, FileWriter tagFile, FileWriter itemFile) {
        saveList(listFile);
        saveTag(tagFile);
        saveItem(itemFile);
    }


}
