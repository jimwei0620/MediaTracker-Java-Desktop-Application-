package model;

import exceptions.DataExistAlreadyException;
import exceptions.ItemNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.SaveAble;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.FileWriter;
import java.util.*;


// class which manages tags and tagged items
public class TagManager implements SaveAble {
    private Map<Tag, ArrayList<MediaItem>> tagAndItem;
    private static TagManager instance;

    // MODIFIES: this
    // EFFECTS: initialize tagAndItem (empty)
    private TagManager() {
        tagAndItem = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: Get instance of TagManager
    public static TagManager getInstance() {
        if (instance == null) {
            instance = new TagManager();
        }
        return instance;
    }

    // MODIFIES: this
    // EFFECTS: remove instance of TagManager
    public static void removeInstance() {
        instance = null;
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
            ItemManager.getInstance().removeInactiveItem(item);
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


    @Override
    public void save(FileWriter listFile, FileWriter tagFile, FileWriter itemFile) {
        JSONArray arrayOfTags = new JSONArray();
        for (Tag tag: tagAndItem.keySet()) {
            JSONObject tagObject = tag.save();
            arrayOfTags.put(tagObject);
        }
        arrayOfTags.write(tagFile);
    }
}
